package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.carradatapipeline.pipeline.FetchFromInform;
import edu.chip.carranet.carradatapipeline.pipeline.InformI2b2Processor;
import edu.chip.carranet.carradatapipeline.pipeline.OdmIgnoreList;
import edu.chip.carranet.carradatapipeline.pipeline.SqlOutputter;
import edu.chip.carranet.carradatapipeline.sitemap.SiteMap;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionStore;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionDBStore;
import edu.chip.carranet.carradatapipeline.transactionstore.SiteTransactionStore;
import edu.chip.carranet.importpipeline.IngestionEngine;
import edu.chip.carranet.importpipeline.config.FetchConfig;
import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.fetch.Fetcher;
import edu.chip.carranet.importpipeline.output.Outputter;
import edu.chip.carranet.importpipeline.process.Processor;
import edu.chip.carranet.inform.InformClient;
import edu.chip.carranet.inform.InformConfig;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Justin Quan
 *          Date: Oct 19, 2010
 */
public class ImportPipeline {
    private static final Logger log = Logger.getLogger(ImportPipeline.class);


    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();  // log4j

        // setup config sources
        CompositeConfiguration config = new CompositeConfiguration();
        URL pipelinePropertiesURL = ImportPipeline.class.getResource("/pipeline.properties");
        if(pipelinePropertiesURL != null) {
            config.addConfiguration(new PropertiesConfiguration(pipelinePropertiesURL));
            log.info("loaded config " + pipelinePropertiesURL);
        } else {
            log.info("couldn't find pipeline.properties");
        }
        config.addConfiguration(new PipelineConfig());
        dumpConfig(config);

        // build objects
        String transactionTableName = config.getString(PipelineConfig.TRANSACTION_TABLE_NAME);
        String siteTableName = config.getString(PipelineConfig.SITE_TRANSACTION_TABLE_NAME);
        String connectionString = config.getString(PipelineConfig.DB_CONNECTION_STRING);
        String user = config.getString(PipelineConfig.DB_USER);
        String passwd = config.getString(PipelineConfig.DB_PASSWORD);

        InformTransactionStore transactionStore = new InformTransactionDBStore(connectionString, user, passwd, transactionTableName);
        SiteTransactionStore siteTransactionStore = new SiteTransactionDBStore(connectionString, user, passwd, siteTableName);

        // sadly required
        InformClient.disableSSLTrust();

        InformConfig informConfig = new InformConfig(config.getString(PipelineConfig.INFORM_TRIAL),
                config.getString(PipelineConfig.INFORM_USERNAME),
                config.getString(PipelineConfig.INFORM_PASSWORD),
                config.getString(PipelineConfig.INFORM_ENDPOINT),
                config.getBoolean(PipelineConfig.INFORM_USE_WS_SECURITY));
        FetchConfig fetchConfig = new FetchConfig(config.getLong(PipelineConfig.FETCHER_PERIOD),
                TimeUnit.valueOf(config.getString(PipelineConfig.FETCHER_TIMEUNIT)));
        SiteMap siteMap = new SiteMap(config.getString(PipelineConfig.SITEMAP_FILENAME));



        String formIgnoreListFilename = config.getString(PipelineConfig.ODM_IGNORE_LIST_FORM_FILENAME);
        String itemIgnoreListFilename = config.getString(PipelineConfig.ODM_IGNORE_LIST_ITEM_FILENAME);

        OdmIgnoreList ignoreList = new OdmIgnoreList(new File(formIgnoreListFilename), new File(itemIgnoreListFilename));

        FetchFromInform fetcher = new FetchFromInform(informConfig, fetchConfig, transactionStore);
        Processor processor = new InformI2b2Processor(ignoreList, connectionString, user, passwd);
        SqlOutputter outputter = new SqlOutputter(fetcher, siteTransactionStore, siteMap);
        outputter.setDoBatch(config.getBoolean(PipelineConfig.SQL_OUTPUTTER_USE_BATCH));

        Map<Dispatchable.DispatchType,Processor> processorMap = new HashMap<Dispatchable.DispatchType,Processor>();
        processorMap.put(Dispatchable.DispatchType.ODMData, processor);
        Map<Dispatchable.DispatchType,Outputter> outputterMap = new HashMap<Dispatchable.DispatchType, Outputter>();
        outputterMap.put(Dispatchable.DispatchType.SQLCommands, outputter);

        final IngestionEngine engine = new IngestionEngine(Collections.singletonList((Fetcher)fetcher),
                processorMap, outputterMap);

        Thread shutdownThread = new Thread() {
            @Override
            public void run() {
                engine.stop();
                try {
                    engine.getShutDownLatch().await(60, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    log.info("Interrupted while waiting for shutdown to complete", e);
                }
            }
        };
        shutdownThread.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(shutdownThread);

        log.info("configuration complete, starting import engine");
        engine.start();

        // in the future, we can add an external hook to this latch so we can count it down.
        try {
            engine.getShutDownLatch().await();
        } catch (InterruptedException e) {
            log.warn("someone interrupted my eternal wait, and i will now exit.", e);
        }
    }

    private static void dumpConfig(CompositeConfiguration config) {
                // build objects
        log.info(PipelineConfig.TRANSACTION_TABLE_NAME + ": " + config.getString(PipelineConfig.TRANSACTION_TABLE_NAME));
        log.info(PipelineConfig.SITE_TRANSACTION_TABLE_NAME + ": " + config.getString(PipelineConfig.SITE_TRANSACTION_TABLE_NAME));
        log.info(PipelineConfig.DB_CONNECTION_STRING + ": " + config.getString(PipelineConfig.DB_CONNECTION_STRING));
        log.info(PipelineConfig.DB_USER + ": " + config.getString(PipelineConfig.DB_USER));
//        log.info("password: " + ": " + config.getString(PipelineConfig.DB_PASSWORD));
        log.info(PipelineConfig.INFORM_TRIAL + ": " + config.getString(PipelineConfig.INFORM_TRIAL));
        log.info(PipelineConfig.INFORM_USERNAME + ": " + config.getString(PipelineConfig.INFORM_USERNAME));
//        log.info("asdf: " + ": " + config.getString(PipelineConfig.INFORM_PASSWORD));
        log.info(PipelineConfig.INFORM_ENDPOINT + ": " + config.getString(PipelineConfig.INFORM_ENDPOINT));
        log.info(PipelineConfig.INFORM_USE_WS_SECURITY + ": " + config.getBoolean(PipelineConfig.INFORM_USE_WS_SECURITY));
        log.info(PipelineConfig.FETCHER_PERIOD + ": " + config.getLong(PipelineConfig.FETCHER_PERIOD));
        log.info(PipelineConfig.FETCHER_TIMEUNIT + ": " + TimeUnit.valueOf(config.getString(PipelineConfig.FETCHER_TIMEUNIT)));
        log.info(PipelineConfig.SITEMAP_FILENAME + ": " + config.getString(PipelineConfig.SITEMAP_FILENAME));
        log.info(PipelineConfig.ODM_IGNORE_LIST_FORM_FILENAME + ": " + config.getString(PipelineConfig.ODM_IGNORE_LIST_FORM_FILENAME));
        log.info(PipelineConfig.ODM_IGNORE_LIST_ITEM_FILENAME + ": " + config.getString(PipelineConfig.ODM_IGNORE_LIST_ITEM_FILENAME));


    }
}
