package net.shrine.broadcaster.sitemapping;


import edu.chip.carranet.jaxb.MachineEntry;
import edu.chip.carranet.jaxb.Machines;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.X509Principal;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RoutingTableSiteNameMapper implements SiteNameMapper {

    private static Logger log = Logger.getLogger(RoutingTableSiteNameMapper.class);
    private List<MachineEntry> machineList = new ArrayList<MachineEntry>();


    public RoutingTableSiteNameMapper(String address) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(address);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                Reader r = new InputStreamReader(entity.getContent());
                Machines machines = JAXBUtils.unmarshal(r, Machines.class);
                if(machines != null) {
                    machineList = machines.getMachineList();
                }
                //trim out newlines, they screw up everything
                for(MachineEntry m : machineList) {
                    m.setMachineId(m.getMachineId().trim());
                }
            }
        } catch(MalformedURLException e) {
            log.fatal("MaformedURL exceptions shouldn't be happening here, don't store invalid URLS in OLS!!!!");
            //can't happen, it's hard coded
            throw new RuntimeException(e);
        } catch(IOException e) {
            log.fatal("Fatal IOException");
        } catch(JAXBException e) {
            log.fatal("Fatal JAXBException");
        }
    }


    /**
     * This method takes the DN from SPIN metadata and coverts it
     * into something useful
     *
     * @param dn
     * @return
     */
    @Override
    public String getSiteIdentifierFromDN(String dn) {
        final X509Principal principal = new X509Principal(dn);
        final Vector<?> values = principal.getValues(X509Name.CN);
        final String cn = (String) values.get(0);
        return getSiteIdentifierFromHostname(cn);
    }

    @Override
    public String getSiteIdentifierFromHostname(String hostName) {
        try {
            for(MachineEntry m : machineList) {
                URL url = new URL(m.getLocator());
                if(url.getHost().trim().equalsIgnoreCase(hostName.trim())) {
                    return m.getMachineId();
                }
            }
        } catch(MalformedURLException e) {
            //cant' happen we parse it on the way in
        }

        return "Unknown Site";
    }


}
