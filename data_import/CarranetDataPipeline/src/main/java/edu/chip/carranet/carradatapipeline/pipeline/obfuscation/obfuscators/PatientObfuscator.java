package edu.chip.carranet.carradatapipeline.pipeline.obfuscation.obfuscators;

import edu.chip.carranet.carradatapipeline.pipeline.obfuscation.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * David Ortiz
 * <p/>
 * Master Obfuscator class
 */
public class PatientObfuscator implements IObfuscator {

    List<IObfuscator> obfuscatorList;


    public PatientObfuscator() {
        obfuscatorList = new ArrayList<IObfuscator>();
        obfuscatorList.add(new ZipCodeObfuscator());
        obfuscatorList.add(new BirthdayObfuscator());
        obfuscatorList.add(new OnsetDateObfuscator());
        obfuscatorList.add(new BiopsyObfuscator());
        obfuscatorList.add(new RheumDTObfuscator());
        obfuscatorList.add(new VisitDateObfuscator());
        obfuscatorList.add(new SubjectKeyObfuscator());
        obfuscatorList.add(new AuditRecordObfuscator());
        obfuscatorList.add(new FreeTextObfuscator());
        obfuscatorList.add(new ReasonForChangeObfuscator());
    }

    @Override
    public void obfuscate(Patient p) {
        for (IObfuscator obs : obfuscatorList) {
            obs.obfuscate(p);
        }
    }
}
