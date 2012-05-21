package edu.chip.carranet.carradatapipeline.sitemap;

import edu.chip.carranet.jaxb.SiteMapping;
import edu.chip.carranet.jaxb.SiteType;
import org.apache.log4j.Logger;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SiteMap implements ISiteMap {
    private static final Logger log = Logger.getLogger(SiteMap.class);
    private Map<String, SiteType> siteMap = new HashMap<String, SiteType>();

    public SiteMap() throws IOException {
        this("sitemap.xml");
    }

    public SiteMap(String fileName) throws IOException {
        this(new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile()));
    }

    public SiteMap(File siteFile) throws IOException {
        SiteMapping m = null;
        try {
            m = JAXBUtils.unmarshal(siteFile, SiteMapping.class);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
        for (SiteType site : m.getSite()) {
            siteMap.put(site.getSiteid(), site);
        }
    }

    public SiteMap(Map<String, SiteType> siteMap) {
        this.siteMap = siteMap;
    }

    public Connection getConnection(String identifier) throws SQLException {
        SiteType site = siteMap.get(identifier);
        if(site == null) {
            throw new SQLException("do not have a site mapping for " + identifier);
        }

        // incomplete information will be treated as a null connection (tri-state)
        if(isNullOrEmpty(site.getConnectionString()) ||
                isNullOrEmpty(site.getUsername()) ||
                isNullOrEmpty(site.getPassword())) {
            return null;
        }

        return DriverManager.getConnection(site.getConnectionString(),
                site.getUsername(),
                site.getPassword());
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
