package edu.chip.carranet.carradatapipeline.obfuscation;


import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.RandomDateGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestRandomDateGenerator {


    @Test
    public void testRandomDatesBetween() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();


        c1.add(Calendar.DAY_OF_YEAR, -200);
        c2.add(Calendar.DAY_OF_YEAR, 100);

        List<Date> returnList = new ArrayList<Date>();

        for (int i = 0; i < 500; i++) {

            returnList.add(RandomDateGenerator.generateBetween(c1.getTime(), c2.getTime()));

        }

        assertEquals(500, returnList.size());

        for (Date d : returnList) {
            assertEquals(true, d.after(c1.getTime()) && d.before(c2.getTime()));
        }
    }


}
