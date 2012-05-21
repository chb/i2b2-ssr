package edu.carranet.client.overlay;

/**
 * Will be an interface one day.  not today.
 *
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 30, 2010
 */
public class OLSCredentials {
    String creds;

    public OLSCredentials(String creds) {
        this.creds = creds;
    }

    public String getCreds() {
        return creds;
    }

    public void setCreds(String creds) {
        this.creds = creds;
    }
}
