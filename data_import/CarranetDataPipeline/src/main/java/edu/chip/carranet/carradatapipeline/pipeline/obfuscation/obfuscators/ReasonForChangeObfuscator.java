package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;

import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import org.cdisc.ns.odm.v1.ItemData;
import org.cdisc.ns.odm.v1.ReasonForChange;

import java.util.List;


/**
 * This rolls through the reason for change field
 */
public class ReasonForChangeObfuscator implements IObfuscator {

    public static final String matcher = "(Transcription Error|Screening Log Entry|New Information|Item copied from previous form|Initial Entry|Enrollment Entry|Changed Information|Actual Visit Date Updated|Other Reason)";


    @Override
    public void obfuscate(Patient p) {
        for (List<ItemData> dList : p.getObservations().values()) {
            for (ItemData d : dList) {
                if (d.getAuditRecord() != null && d.getAuditRecord().getReasonForChange() != null) {

                    ReasonForChange f = d.getAuditRecord().getReasonForChange();
                    if (!f.getValue().matches(matcher)) {
                        f.setValue("Other Reason");
                    }

                }

            }
        }
    }
}
