package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;


import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;

public interface IObfuscator {


    //modify in place
    public void obfuscate(final Patient p);

}
