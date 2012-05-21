package edu.chip.carranet.carradatapipeline.pipeline;

import com.phaseforward.informadapter.odm._2.ResponseODM;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionRecord;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionStore;
import edu.chip.carranet.carradatapipeline.transactionstore.TransactionException;
import edu.chip.carranet.importpipeline.config.FetchConfig;
import edu.chip.carranet.importpipeline.fetch.Fetcher;
import edu.chip.carranet.inform.InformClient;
import edu.chip.carranet.inform.InformClientAPI;
import edu.chip.carranet.inform.InformConfig;
import org.apache.log4j.Logger;
import org.cdisc.ns.odm.v1.ClinicalData;
import org.cdisc.ns.odm.v1.ODM;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * This class is responsible for talking to an Inform Server Adapter API to fetch
 * transactions via the GetTransactions call.  This call works via a bookmark for
 * paging long results.  We'll store away that bookmark so that subsequent calls
 * will always return the latest unseen edu.chip.carranet.data.
 *
 * This class is thread safe and synchronizes access to persisted resources
 *
 *
 * @author Justin Quan
 *          Date: Oct 19, 2010
 */
public class FetchFromInform implements Fetcher {
    private static final Logger log = Logger.getLogger(FetchFromInform.class);

    private final FetchConfig fetchConfig;

    private String trial;
    private AtomicBoolean currentlyProcessing;
    private String bookmark;
    private InformClientAPI client;
    private InformTransactionStore transactionStore;

    public FetchFromInform(InformConfig informConfig, FetchConfig fetchConfig, InformTransactionStore transactionStore) throws TransactionException {
        this.trial = informConfig.getTrial();
        this.fetchConfig = fetchConfig;
        this.client = InformClient.fromConfig(informConfig);
        this.transactionStore = transactionStore;
        this.currentlyProcessing = new AtomicBoolean(false);

        bookmark = transactionStore.loadLastBookmark(informConfig.getTrial());
    }

    // Junit constructor
    public FetchFromInform(InformClientAPI client, String trial, FetchConfig fetchConfig, InformTransactionStore transactionStore) throws TransactionException {
        this.fetchConfig = fetchConfig;
        this.client = client;
        this.transactionStore = transactionStore;
        this.currentlyProcessing = new AtomicBoolean(false);
        this.trial = trial;

        bookmark = transactionStore.loadLastBookmark(trial);
    }

    public void notifyProcessed(InformTransactionRecord record) throws TransactionException {
        transactionStore.save(record);
        currentlyProcessing.set(false);
    }

    @Override
    public synchronized InformODMData fetch() throws FetchException {
        log.info("fetching inform data");
        if(currentlyProcessing.get()) {
            log.info("skipping fetch, fetch already in progress");
            return null;  // TODO: trace this contract
        }

        try {
            Map<String,List<ClinicalData>> siteDatas =
                    new HashMap<String,List<ClinicalData>>();

            log.info("fetching transaction");
            ResponseODM responseODM = client.getTransaction(bookmark);
            log.info("transaction fetched");

            // this condition effectively demarcates the end of the edu.chip.carranet.data stream, until more edu.chip.carranet.data comes in
            if(responseODM.getBookmark().equals(bookmark) &&
                    responseODM.getStatus() != null &&
                    responseODM.getStatus().value().equals("END")) {
                log.info("hit the end of the inform data stream");
                return null;
            }

            ODM odm = responseODM.getODM();
            for(ClinicalData data : odm.getClinicalData() ) {
                if(!data.getSubjectData().isEmpty()) {
                    String location = data.getSubjectData().get(0).getSiteRef().getLocationOID();
                    List<ClinicalData> datas = siteDatas.get(location);
                    if(datas == null) {
                        datas = new ArrayList<ClinicalData>();
                        siteDatas.put(location, datas);
                    }
                    datas.add(data);
                }
            }

            InformTransactionRecord informTransactionRecord = new InformTransactionRecord(trial, bookmark,
                    new Timestamp(System.currentTimeMillis()), null, responseODM.getBookmark(), "");

            currentlyProcessing.set(true);

            bookmark = responseODM.getBookmark();

            log.info("returning fetched data");
            return new InformODMData(informTransactionRecord, siteDatas, odm);
        } catch (IOException ioe) {
            throw new FetchException(ioe);
        }
    }

    @Override
    public long getFetchPeriod() {
        return fetchConfig.getFetchPeriod();
    }

    @Override
    public TimeUnit getFetchTimeUnit() {
        return fetchConfig.getFetchTimeUnit();
    }
}
