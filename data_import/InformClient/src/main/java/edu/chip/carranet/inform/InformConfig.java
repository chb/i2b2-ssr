package edu.chip.carranet.inform;

/**
 * @author Justin Quan
 * Date: Oct 19, 2010
 */
public class InformConfig {
    private final String trial;
    private final String user;
    private final String pass;
    private final String endpoint;
    private final boolean useWSSecurity;

    public static final InformConfig CHIP_TEST = new InformConfig("carranet", "soatest", "", "http://informadapterdev.chboston.org/informadapter/odm/informodm.asmx", false);

    public InformConfig(String trial, String user, String pass, String endpoint, boolean useWSSecurity) {
        this.trial = trial;
        this.user = user;
        this.pass = pass;
        this.endpoint = endpoint;
        this.useWSSecurity = useWSSecurity;
    }

    public String getTrial() {
        return trial;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public boolean isUsingWSSecurity() {
        return useWSSecurity;
    }

}
