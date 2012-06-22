package edu.chip.carranet.server.backend;

import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.data.MachineStatusEnum;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.security.cert.X509Certificate;


public class PingThread extends Thread {

    private MachineStatusEnum status = MachineStatusEnum.NotResponding;
    private MachineDTO dto;
    private Logger log = Logger.getLogger(PingThread.class);

    public MachineDTO getDto() {
        return dto;
    }

    public MachineStatusEnum getStatus() {
        return status;
    }

    public PingThread(MachineDTO dto) {
        this.dto = dto;
    }

    @Override
    public void run() {

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
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());

//            // Create all-trusting host name verifier
//            HostnameVerifier allHostsValid = new HostnameVerifier() {
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            };
//            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {

            e.printStackTrace();
        }


        try {

            URL url = new URL(dto.getUri());
            if (InetAddress.getByName(url.getHost()).isReachable(1000)) {
                status = MachineStatusEnum.Responding;
            }
            Object o = url.getContent();
            //if this doesn't throw an exception I think we're ok, or at least something is running at
            //the URI
            //TODO make this check not suck
            status = MachineStatusEnum.ShrineOk;
        } catch (IOException e) {
            log.debug("Unable to reach " + dto.getUri());

        }


    }
}