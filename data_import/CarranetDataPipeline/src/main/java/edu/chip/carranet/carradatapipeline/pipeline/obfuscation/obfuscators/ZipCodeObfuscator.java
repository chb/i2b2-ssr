package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;


import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import org.cdisc.ns.odm.v1.ItemData;

import java.security.SecureRandom;

public class ZipCodeObfuscator implements IObfuscator {

    SecureRandom sr = new SecureRandom();

    @Override
    public void obfuscate(final Patient p) {
        for (ItemData d : p.getObservations(Patient.ZIP_CODE)) {
            d.setValue(Long.toString(Math.abs(sr.nextLong())).substring(0, 5));
        }
    }
}
