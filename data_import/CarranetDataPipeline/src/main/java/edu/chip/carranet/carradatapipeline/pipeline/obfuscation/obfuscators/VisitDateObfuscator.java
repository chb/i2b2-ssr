package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;

import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.RandomDateGenerator;
import org.cdisc.ns.odm.v1.ItemData;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class VisitDateObfuscator implements IObfuscator {
    public static final Date VISIT_START_DATE = new DateTime("2008-07-01T00:00:00-05:00").toDate();
    public static final Date VISITS_END_DATE = new DateTime("2010-12-31T00:00:00-05:00").toDate();


    @Override
    public void obfuscate(Patient p) {
        List<ItemData> itemDataList = p.getObservations(Patient.VISDT);

        Date latestOnset = p.getLatestOnsetDate();
        Date latestRheum = p.getLatestRheumDate();

        Date latest = null;

        if (latestOnset != null && latestRheum != null) {
            if (latestOnset.before(latestRheum)) {
                latest = latestRheum;
            } else {
                latest = latestOnset;
            }
        } else if (latestOnset == null && latestRheum != null) {
            latest = latestRheum;
        } else if (latestRheum == null && latestOnset != null) {
            latest = latestOnset;
        } else {
            latest = p.getLatestBirthday();
        }


        Calendar calendar = Calendar.getInstance();
        for (ItemData element : itemDataList) {
            DateTime dt = new DateTime(RandomDateGenerator.generateBetween(latest, Patient.STUDY_END_DATE.toDate()));
            element.setValue(dt.toString());
        }
    }
}
