package edu.chip.carranet.inform;

import com.phaseforward.informadapter.odm._1.DownloadAdminDataODMResult;
import com.phaseforward.informadapter.odm._1.DownloadMetadataODMResult;
import com.phaseforward.informadapter.odm._1.GetPatientForms;
import com.phaseforward.informadapter.odm._1.GetPatientFormsResult;
import com.phaseforward.informadapter.odm._1.GetPatientList;
import com.phaseforward.informadapter.odm._1.GetPatientListResult;
import com.phaseforward.informadapter.odm._1.GetSiteList;
import com.phaseforward.informadapter.odm._1.GetSiteListResult;
import com.phaseforward.informadapter.odm._1.GetTransactionsResult;
import com.phaseforward.informadapter.odm._1.InFormODM;
import com.phaseforward.informadapter.odm._1.InFormODMSoap;
import com.phaseforward.informadapter.odm._1.Node;
import com.phaseforward.informadapter.odm._2.DownloadAdminDataODM;
import com.phaseforward.informadapter.odm._2.DownloadMetadataODM;
import com.phaseforward.informadapter.odm._2.DownloadTransactionODM;
import com.phaseforward.informadapter.odm._2.ResponseODM;
import org.apache.log4j.Logger;
import org.spin.tools.JAXBUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.AddressingFeature;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Quan
 *         Date: Dec 17, 2010
 */
public class InformClient implements InformClientAPI {
    private static final Logger log = Logger.getLogger(InformClient.class);
    private static final String INFORM_USER = "soatest";

    private String trial;
    private InFormODMSoap informProxy;

    public static InformClient fromConfig(InformConfig config) {
        if (config.isUsingWSSecurity()) {
            return new InformClient(config.getTrial(), config.getUser(), config.getPass(), config.getEndpoint());
        } else {
            return new InformClient(config.getTrial(), config.getEndpoint());
        }
    }

    public InformClient(String trial, String wsdlUrl) {
        this.trial = trial;

        InFormODM informODM = new InFormODM();
        log.info("connecting to " + wsdlUrl);
        informProxy = informODM.getInFormODMSoap();

        // Use Proxy Instance as BindingProvider to configure RequestContext with endpoint's URL
        BindingProvider bp = (BindingProvider) informProxy;
        Map rc = bp.getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlUrl);
    }

    /**
     * WSSecurity enabled constructor
     *
     * @param trial   the trial name
     * @param user    the user name to authenticate to the inform server with
     * @param pass    the password to authenticate to the inform server with
     * @param wsdlUrl the endpoint of the inform server wsdl
     */
    public InformClient(String trial, String user, String pass, String wsdlUrl) {
        this.trial = trial;

        InFormODM informODM = new InFormODM();
        log.info("connecting to " + wsdlUrl);
        informProxy = informODM.getInFormODMSoap(new AddressingFeature(true));

        log.info("setting up WSSecurity tokens");
        // Use Proxy Instance as BindingProvider to configure RequestContext with endpoint's URL
        BindingProvider bp = (BindingProvider) informProxy;
        Map rc = bp.getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlUrl);

        List<Handler> handlerList = new ArrayList<Handler>();
        handlerList.add(new SoapUserTokenHandler(user, pass));
        bp.getBinding().setHandlerChain(handlerList);

    }

    public String getSiteList() throws IOException {
        try {
            GetSiteList getSiteList = new GetSiteList();
            getSiteList.setTrial(trial);
            Node node = new Node();
            node.setAny(getSiteList);

            GetSiteListResult result = informProxy.getSiteList(node);
            return JAXBUtils.marshalToString(result);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    public String getPatientList(String site) throws IOException {
        try {
            GetPatientList request = new GetPatientList();
            request.setTrial(trial);
            request.setSiteID(site);
            Node node = new Node();
            node.setAny(request);

            GetPatientListResult result = informProxy.getPatientList(node);
            return JAXBUtils.marshalToString(result);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    public String getPatientForms(String patient) throws IOException {
        try {
            GetPatientForms request = new GetPatientForms();
            request.setTrial(trial);
            request.setPatientID(Long.parseLong(patient));
            Node node = new Node();
            node.setAny(request);

            GetPatientFormsResult result = informProxy.getPatientForms(node);
            return JAXBUtils.marshalToString(result);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    public String getMetadata() throws IOException {
        try {
            DownloadMetadataODM request = new DownloadMetadataODM();
            request.setTrial(trial);
            request.setUserName(INFORM_USER);
            Node node = new Node();
            node.setAny(request);

            DownloadMetadataODMResult result = informProxy.downloadMetadataODM(node);
            return JAXBUtils.marshalToString(result);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    public String getAdminMetadata() throws IOException {
        try {
            DownloadAdminDataODM request = new DownloadAdminDataODM();
            request.setTrial(trial);
            request.setUserName(INFORM_USER);
            Node node = new Node();
            node.setAny(request);

            DownloadAdminDataODMResult result = informProxy.downloadAdminDataODM(node);
            return JAXBUtils.marshalToString(result);
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    public ResponseODM getTransaction(String bookmark) throws IOException {
        log.info("start getTransaction");
        DownloadTransactionODM request = new DownloadTransactionODM();
        request.setTrial(trial);
        request.setUserName(INFORM_USER);
        request.setBookmark(bookmark);
        request.setIncludeExtensions(false);
        Node node = new Node();
        node.setAny(request);

        log.info("start informProxy call");
        GetTransactionsResult result = informProxy.getTransactions(node);
        log.info("finish informProxy call");
        ResponseODM response = ((JAXBElement<ResponseODM>) result.getAny()).getValue();

        return response;
    }

    public static void disableSSLTrust() throws KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                                           String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs,
                                           String authType) {
            }
        }};

        // Install the all-trusting trust manager
        SSLContext sc;
        sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection
                .setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public static void main(String[] args) throws Exception {
        disableSSLTrust();
//        InformClient client = new InformClient("carranet", "http://informadapterdev.chboston.org/informadapter/odm/informodm.asmx");
        InformClient client = new InformClient("crnt_regst", "https://adapter.dcri.duke.edu/informadapter/odm/informodm.asmx");

//        System.out.println(client.getSiteList());
//        System.out.println(client.getPatientList("15186"));
//        System.out.println(client.getPatientForms("15808"));
//        System.out.println(client.getMetadata());
//        System.out.println(client.getAdminMetadata());

        String oldBookmark = "";
        String nextBookmark = "";

        ResponseODM response = client.getTransaction(args[0]);
        Marshaller m = InformJAXBContext.getInstance().getContext().createMarshaller();
        m.setProperty("jaxb.formatted.output", true);
        StringWriter sw = new StringWriter();
        m.marshal(response, sw);
        System.out.println(sw.toString());


//        int i = 0;
//        do {
//            System.out.println("querying with bookmark: " + nextBookmark);
//            ResponseODM response = client.getTransaction(nextBookmark);
//            Marshaller m = InformJAXBContext.getInstance().getContext().createMarshaller();
//            m.setProperty("jaxb.formatted.output", true);
//            StringWriter sw = new StringWriter();
//            m.marshal(response, sw);
////            System.out.println(sw.toString());
//            File f = new File("/tmp/inform-"+i);
//            FileWriter fw = new FileWriter(f);
//            fw.write(sw.toString());
//            fw.flush();
//            fw.close();
//            ++i;
//            oldBookmark = nextBookmark;
//            nextBookmark = response.getBookmark();
//            System.out.println("response contains bookmark: " + nextBookmark);
//        } while (!oldBookmark.equals(nextBookmark));

    }
}
