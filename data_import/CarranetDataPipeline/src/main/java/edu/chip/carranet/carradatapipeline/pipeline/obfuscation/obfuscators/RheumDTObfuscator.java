package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;


import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.RandomDateGenerator;
import org.cdisc.ns.odm.v1.ItemData;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Obfuscates RheumDT dates
 */
public class RheumDTObfuscator implements IObfuscator {

    @Override
    public void obfuscate(Patient p) {

        List<ItemData> itemDataList = p.getObservations(Patient.RHEUMDT);

        Date lastOnsetDate = p.getLatestOnsetDate();

        if (lastOnsetDate == null) {
            lastOnsetDate = p.getLatestBirthday();
        }

        Date firstVisitDate = p.getFirstVisitDate();
        if (firstVisitDate == null) {
            firstVisitDate = Patient.STUDY_END_DATE.toDate();
        }

        for (ItemData d : itemDataList) {
            Date newDate = RandomDateGenerator.generateBetween(lastOnsetDate, firstVisitDate);
            d.setValue(new DateTime(newDate).toString());
        }

    }
}
