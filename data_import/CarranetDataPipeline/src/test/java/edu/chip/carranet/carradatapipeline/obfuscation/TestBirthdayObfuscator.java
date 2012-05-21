package edu.chip.carranet.carradatapipeline.obfuscation;


import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators.BirthdayObfuscator;
import org.cdisc.ns.odm.v1.ItemData;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class TestBirthdayObfuscator {

    private static boolean showGraphs = true;

    @Test
    public void testBirthdayObfuscator() throws Exception {

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();

        c1.add(Calendar.DAY_OF_YEAR, -20);
        c2.add(Calendar.DAY_OF_YEAR, -100);
        c3.add(Calendar.DAY_OF_YEAR, -140);
        c4.add(Calendar.DAY_OF_YEAR, -32);

        DateTime dt1 = new DateTime(c1.getTime().getTime());
        DateTime dt2 = new DateTime(c2.getTime().getTime());
        DateTime dt3 = new DateTime(c3.getTime().getTime());
        DateTime dt4 = new DateTime(c4.getTime().getTime());

        List<DateTime> dtList = new ArrayList<DateTime>();
        dtList.add(dt1);
        dtList.add(dt2);
        dtList.add(dt3);
        dtList.add(dt4);

        ItemData d1 = new ItemData();
        d1.setItemOID(Patient.DEMO_BDAY);
        d1.setValue(dt1.toString());

        ItemData d2 = new ItemData();
        d2.setItemOID(Patient.DEMO_BDAY);
        d2.setValue(dt2.toString());

        ItemData d3 = new ItemData();
        d3.setItemOID(Patient.DEMO_BDAY);
        d3.setValue(dt3.toString());

        ItemData d4 = new ItemData();
        d4.setItemOID(Patient.DEMO_BDAY);
        d4.setValue(dt4.toString());

        Patient p = new Patient();
        p.addItem(d1);
        p.addItem(d2);
        p.addItem(d3);
        p.addItem(d4);

        BirthdayObfuscator bo = new BirthdayObfuscator();
        bo.obfuscate(p);
        List<ItemData> transformedList = p.getObservations(Patient.DEMO_BDAY);


        for (int i = 0; i < transformedList.size(); i++) {
            Date d = new DateTime(transformedList.get(i).getValue()).toDate();
            assertEquals(true, withinYear(d));
        }

    }


    private boolean withinYear(Date d) {
        Calendar sixBack = Calendar.getInstance();
        sixBack.setTime(d);
        sixBack.add(Calendar.MONTH, -6);

        Calendar sixFront = Calendar.getInstance();
        sixFront.add(Calendar.MONTH, 6);

        return d.after(sixBack.getTime()) && d.before(sixFront.getTime());


    }


}
