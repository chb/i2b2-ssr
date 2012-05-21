package edu.chip.carranet.carradatapipeline.pipeline.obfuscation;


import java.security.SecureRandom;
import java.util.Date;

/**
 * Utility Class for generating random dates
 */
public class RandomDateGenerator {


    /**
     * @param start
     * @param end
     * @return
     */
    public static Date generateBetween(Date start, Date end) {

        assert (start != null);
        assert (end != null);
        assert (start.before(end));

        SecureRandom sr = new SecureRandom();
        long milliStart = start.getTime();
        long milliEnd = end.getTime();

        long mod = milliEnd - milliStart;


        long randomMilliStart = milliStart + (Math.abs(sr.nextLong()) % mod);
        Date d = new Date(randomMilliStart);


        return d;

    }


}
