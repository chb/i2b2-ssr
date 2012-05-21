package edu.carranet.integration.tools;


import au.com.bytecode.opencsv.CSVReader;
import edu.carranet.client.auth.IAuthClient;
import edu.carranet.client.auth.RestletAuthClient;
import edu.carranet.client.auth.Token;
import edu.carranet.client.overlay.IOLSClient;
import edu.carranet.client.overlay.OLSClient;
import edu.carranet.client.overlay.OLSCredentials;
import edu.chip.carranet.jaxb.Machine;
import edu.chip.carranet.jaxb.MachineEntry;

import java.io.FileReader;

public class PopulateOLS {
	
    public static final String DEFAULT_USERNAME = "user";
    public static final String DEFAULT_PASSWORD = "pass";

    public static void main(String[] args) throws Exception {
        String file = Thread.currentThread().getContextClassLoader().getResource("ols_entries.csv").getFile();
        CSVReader reader = new CSVReader(new FileReader(file), ',', '\"', 1);
	


        String [] nextLine;


        IAuthClient authClient = new RestletAuthClient("http://carra-core-production:8182/");

        Token t = authClient.getAuthorization(DEFAULT_USERNAME, DEFAULT_PASSWORD);

        while ((nextLine = reader.readNext()) != null) {
            String siteId = nextLine[1];
            String url = nextLine[2];


            IOLSClient olsClient = new OLSClient("http://carra-core-production:8183/");

            OLSCredentials creds = new OLSCredentials(t.getAuthString());

            Machine m = new Machine();
            MachineEntry machineEntry = new MachineEntry();

            System.out.println("Inserting machine " + siteId + " With URL " + url);
            m.setMachineEntry(machineEntry);
            machineEntry.setLocator(url);
            machineEntry.setMachineId(siteId);
            olsClient.createMachine(creds,m);

        }


    }

}
