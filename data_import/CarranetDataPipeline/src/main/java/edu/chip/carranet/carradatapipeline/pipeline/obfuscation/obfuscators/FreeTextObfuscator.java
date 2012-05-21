package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;


import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;
import org.cdisc.ns.odm.v1.ItemData;

public class FreeTextObfuscator implements IObfuscator {

    @Override
    public void obfuscate(Patient p) {


        for (ItemData d : p.getFreeTextFields()) {
            d.setValue("please specify");

        }
    }
}
