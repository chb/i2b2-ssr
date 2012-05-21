package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;

import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class SubjectKeyObfuscator implements IObfuscator {

    Map<String, String> oldToNewMap = new HashMap<String, String>();

    @Override
    public void obfuscate(Patient p) {
        Random rand = new SecureRandom();
        String newSubjectKey = Long.toString(Math.abs(rand.nextLong())).substring(0,5);

        p.setSubjectKey(newSubjectKey);


    }


}
