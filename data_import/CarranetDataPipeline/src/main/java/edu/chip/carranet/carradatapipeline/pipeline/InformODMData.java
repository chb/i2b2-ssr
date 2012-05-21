package edu.chip.carranet.carradatapipeline.pipeline;

import edu.chip.carranet.carradatapipeline.transactionstore.InformTransactionRecord;
import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import org.cdisc.ns.odm.v1.ClinicalData;
import org.cdisc.ns.odm.v1.ODM;

import java.util.List;
import java.util.Map;

/**
 * @author Justin Quan
 * Date: 2/25/11
 */
public class InformODMData extends Dispatchable {
    private InformTransactionRecord transactionRecord;
    private Map<String,List<ClinicalData>> siteData;
    private ODM odm;  // preserve the original edu.chip.carranet.data, to be used later to generate an audit report

    public InformODMData(InformTransactionRecord transactionRecord, Map<String, List<ClinicalData>> siteData, ODM odm) {
        this.transactionRecord = transactionRecord;
        this.siteData = siteData;
        this.odm = odm;
    }

    public Map<String, List<ClinicalData>> getSiteData() {
        return siteData;
    }

    @Override
    public DispatchType getDispatchType() {
        return Dispatchable.DispatchType.ODMData;
    }

    public InformTransactionRecord getTransactionRecord() {
        return transactionRecord;
    }

    public ODM getOdm() {
        return odm;
    }
}
