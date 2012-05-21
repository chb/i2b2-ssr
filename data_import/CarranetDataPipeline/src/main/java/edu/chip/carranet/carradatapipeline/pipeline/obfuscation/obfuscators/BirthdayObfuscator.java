package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;


import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.RandomDateGenerator;
import org.apache.log4j.Logger;
import org.cdisc.ns.odm.v1.ItemData;
import org.joda.time.DateTime;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BirthdayObfuscator implements IObfuscator {
    private static Logger log = Logger.getLogger(BirthdayObfuscator.class);


    public BirthdayObfuscator() {
        Calendar c1 = Calendar.getInstance();
    }


    @Override
    public void obfuscate(final Patient p) {
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        String startingBirthday = null;

        //Get last demo birthday
        List<ItemData> demoItemData = p.getObservations(Patient.DEMO_BDAY);

        List<ItemData> screenItemData = p.getObservations(Patient.SCREEN_BDAY);

        if (demoItemData.size() > 0) {
            startingBirthday = demoItemData.get(demoItemData.size() - 1).getValue();
        } else if (demoItemData.size() == 0 && screenItemData.size() > 0) {
            startingBirthday = screenItemData.get(screenItemData.size() - 1).getValue();
        } else {
            throw new RuntimeException("No birthday's for this patient, this is really really bad");
        }

        String newBirthdayValue = generateRandomBday(startingBirthday);

        List<ItemData> allBirthdays = new ArrayList<ItemData>();

        allBirthdays.addAll(demoItemData);
        allBirthdays.addAll(screenItemData);

        for (ItemData d : allBirthdays) {
            d.setValue(newBirthdayValue);
        }


        return;

    }

    protected String generateRandomBday(String birthdayStringValue) {

        DateTime birthdayDateTime = new DateTime(Patient.fixMessupDateString(birthdayStringValue));
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(birthdayDateTime.toDate());
        c2.setTime(birthdayDateTime.toDate());
        c1.add(Calendar.MONTH, -24);
        c2.add(Calendar.MONTH, 24);

        DateTime dt = new DateTime(RandomDateGenerator.generateBetween(c1.getTime(), c2.getTime()));
        return dt.toString();


    }
}
