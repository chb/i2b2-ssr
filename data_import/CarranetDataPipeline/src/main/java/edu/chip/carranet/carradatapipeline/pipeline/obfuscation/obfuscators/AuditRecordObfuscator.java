package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;

import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import org.cdisc.ns.odm.v1.DateTimeStamp;
import org.cdisc.ns.odm.v1.ItemData;
import org.cdisc.ns.odm.v1.LocationRef;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class AuditRecordObfuscator implements IObfuscator {


    @Override
    public void obfuscate(Patient p) {

        Date lastVisitDate = p.getLatestVisitDate();
        DateTime dt = new DateTime(lastVisitDate);


        for (List<ItemData> dataList : p.getObservations().values()) {
            for (ItemData d : dataList) {
                dt = dt.plusDays(1);
                DateTimeStamp dts = new DateTimeStamp();
                dts.setValue(dt.toString());
                d.getAuditRecord().getUserRef().setUserOID("obfusc");
                d.getAuditRecord().setDateTimeStamp(dts);
                d.getAuditRecord().setLocationRef(new LocationRef());
            }
        }

    }
}
