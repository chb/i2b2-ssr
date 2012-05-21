package edu.chip.carranet.carradatapipeline;

import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 3/16/11
 */
public class DeidentifierTest {

    @Test
    public void testDateFixer() throws Exception {
        List<String> badDates = new ArrayList<String>();
        badDates.add("1993-05-28T-:-:-+00:00");
        badDates.add("-----T-:-:-+00:00");
        badDates.add("2007-09--T00:00:00+00:00");
        badDates.add("1998---1T00:00:00+00:00");

        for (String date : badDates) {
            DateTime dt = new DateTime(Patient.fixMessupDateString(date));
        }


    }

}
