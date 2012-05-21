package edu.chip.carranet.carradatapipeline.pipeline;

import java.io.*;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * User: justinquan
 * Date: 5/17/11
 * Time: 10:38 PM
 */
public class OdmIgnoreList {
    private Set<String> formIgnoreList;
    private Set<String> itemIgnoreList;

    public OdmIgnoreList(Set<String> formIgnoreList, Set<String> itemIgnoreList) {
        this.formIgnoreList = formIgnoreList;
        this.itemIgnoreList = itemIgnoreList;
    }

    public OdmIgnoreList(String formIgnoreFile, String itemIgnoreFile) throws IOException {
        this(new File(formIgnoreFile), new File(itemIgnoreFile));
    }

    public OdmIgnoreList(File formIgnoreFile, File itemIgnoreFile) throws IOException {
        this.formIgnoreList = new HashSet<String>();
        this.itemIgnoreList = new HashSet<String>();

        processIgnoreList(formIgnoreFile, formIgnoreList);
        processIgnoreList(itemIgnoreFile, itemIgnoreList);
    }

    private void processIgnoreList(File f, Set s) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line = null;
            while((line = br.readLine()) != null) {
                s.add(line);
            }
        } finally {
            if(fr != null) {
                fr.close();
            }
            if(br != null) {
                br.close();
            }
        }
    }

    public Set<String> getFormIgnoreList() {
        return formIgnoreList;
    }

    public Set<String> getItemIgnoreList() {
        return itemIgnoreList;
    }
}
