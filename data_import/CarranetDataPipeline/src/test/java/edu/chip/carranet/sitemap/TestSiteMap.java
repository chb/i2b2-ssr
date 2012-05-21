package edu.chip.carranet.sitemap;


import edu.chip.carranet.jaxb.SiteMapping;
import edu.chip.carranet.jaxb.SiteType;
import org.junit.Test;
import org.spin.tools.JAXBUtils;

public class TestSiteMap {

    @Test
    public void testStuff() throws Exception {
        SiteMapping map = new SiteMapping();

        SiteType site1 = new SiteType();
        site1.setConnectionString("testConnString");
        site1.setPassword("password");
        site1.setSiteid("sideIdFool");
        site1.setUsername("jiveturkey");


        map.getSite().add(site1);

        String s = JAXBUtils.marshalToString(map);
    }
}
