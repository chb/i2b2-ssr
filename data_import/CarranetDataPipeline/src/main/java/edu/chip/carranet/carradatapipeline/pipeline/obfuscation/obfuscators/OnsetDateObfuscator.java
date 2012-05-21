package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;


import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.RandomDateGenerator;
import org.cdisc.ns.odm.v1.ItemData;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class OnsetDateObfuscator implements IObfuscator {


    @Override
    public void obfuscate(Patient p) {

        List<ItemData> itemDataList = p.getObservations(Patient.ONSETDT);
        Date lastBirthDate = p.getLatestBirthday();

        if (lastBirthDate == null) {
            lastBirthDate = Patient.STUDY_START_DATE.toDate();
        }

        for (ItemData d : itemDataList) {
            Date newDate = RandomDateGenerator.generateBetween(lastBirthDate, Patient.STUDY_END_DATE.toDate());
            d.setValue(new DateTime(newDate).toString());
        }


    }


}
