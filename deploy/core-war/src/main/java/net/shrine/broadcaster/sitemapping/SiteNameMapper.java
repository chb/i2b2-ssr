package net.shrine.broadcaster.sitemapping;


/**
 * This class provices a way to map hostnames back to identifiers
 */
public interface SiteNameMapper {

    /**
     * This method takes the DN from SPIN metadata and coverts it
     * into something useful
     *
     * @param dn - the Distinguished name from the SPIN metadata
     * @return readable name
     */
    public String getSiteIdentifierFromDN(String dn);

    /**
     * This method takes a host name and returns the site id
     * @param hostName
     * @return
     */
    public String getSiteIdentifierFromHostname(String hostName);

}
