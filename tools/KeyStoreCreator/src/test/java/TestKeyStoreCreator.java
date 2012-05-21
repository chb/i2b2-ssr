import edu.chip.carranet.keystore.automation.KeystoreGenerator;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestKeyStoreCreator {


    @Test
    public void testLoadX509CertFromFile() throws Exception {
        KeystoreGenerator gen = new KeystoreGenerator();
        URL url = KeystoreGenerator.class.getResource("/ca-cert.pem");

        File f = new File(url.toURI());

        X509Certificate cert = KeystoreGenerator.readCertFromFile(f);
        assertNotNull(cert);
    }

    @Test
    @Ignore
    public void testReadKeyParFromFile() throws Exception {
        KeystoreGenerator gen = new KeystoreGenerator();
        URL url = KeystoreGenerator.class.getResource("/ca-key.pem");

        File f = new File(url.toURI());

        KeyPair key = KeystoreGenerator.readKeyPair(f, null);
        assertNotNull(key);
    }


    @Test
    public void readHostsFromFile() throws Exception {
        KeystoreGenerator gen = new KeystoreGenerator();


        URL url = KeystoreGenerator.class.getResource("/hosts.txt");
        File f = new File(url.toURI());
        List<String> hosts = KeystoreGenerator.readHostsFromInputFile(f);

        assertEquals(hosts.get(0), "shrine-dev-1");
        assertEquals(hosts.get(1), "shrine-dev-2");
        assertEquals(hosts.get(2), "shrine-dev-3");


    }

}
