package edu.chip.carranet.keystore.automation;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;


/**
 * @Author Dave Ortiz
 * <p/>
 * This is a simple program that is a psuedo CA for generating a bunch of keystores for a network
 * <p/>
 * 1) Creates a public/private CA keypair
 * 2) Creates 60 keystores that are ready to be copied onto the host
 * 3) ?
 * 4) profit
 */
public class KeystoreGenerator {

    private static Logger log = Logger.getLogger(KeystoreGenerator.class);
    private KeyPair caKeypair;
    private X509Certificate caCert;
    private String password = "shrineKeyStore";
    private List<String> hosts = new ArrayList<String>();

    private static final String HELP_MESSAGE = "Options for KeystoreGenerator: \n" +
            "\t-privateKeyFile(O) - CA's private key file\n" +
            "\t-inputFile(R) - File containing the list of hosts to generate\n" +
            "\t-pemPasword(O) - password of the .pem containing the private key\n" +
            "\t-certFile(O) - file containing the x.509 public cert of the CA\n" +
            "To generate your own private key and cert\n" +
            "\topenssl genrsa -out key.pem 4096\n" +
            "\topenssl req -new -key key.pem -out request.pem\n" +
            "\topenssl x509 -req -days 365 -in request.pem -signkey key.pem -out certificate.pem\n";


    private static final String PRIVATE_KEY_FILE_OPT = "privateKeyFile";
    private static final String HOST_INPUT_FILE_OPT = "inputFile";
    private static final String PEM_PASSWORD_OPT = "pemPassword";
    private static final String CERTIFICATE_FILE_OPT = "certFile";
    private static final String DEFAULT_PASSWORD = "";

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        //Use jopt to do option parsing
        OptionParser parser = new OptionParser();
        parser.accepts(PRIVATE_KEY_FILE_OPT, "file containing the private CA key in .pem format").withOptionalArg();
        parser.accepts(HOST_INPUT_FILE_OPT, "Newline delimited list of hostnames to generate the keys for").withRequiredArg();
        parser.accepts(PEM_PASSWORD_OPT, "Password for the private key").withOptionalArg();
        parser.accepts(CERTIFICATE_FILE_OPT, "CA's certificate file").withOptionalArg();

        parser.accepts("help", "Print out help");


        OptionSet opts = parser.parse(args);


        if (opts.has("help")) {
            System.out.println(HELP_MESSAGE);
            System.exit(0);
        }

        String hostFile = (String) opts.valueOf(HOST_INPUT_FILE_OPT);

        if (hostFile == null) {
            log.info("No host file passed in, trying to fine 'shrine-hosts' on classpath");
            hostFile = Thread.currentThread().
                    getContextClassLoader().getResource("shrine-hosts").getFile();


        }


        KeystoreGenerator gen;

        //Let this thing generate a public/private keypair
        if (!opts.has(PRIVATE_KEY_FILE_OPT)) {


            String privateKeyFile = Thread.currentThread().
                    getContextClassLoader().getResource("ca-key.pem").getFile();

            String caCert = Thread.currentThread().
                    getContextClassLoader().getResource("ca-cert.pem").getFile();

            //IF something's missing we generate our own keys/certs
            if (privateKeyFile == null || caCert == null) {
                gen = new KeystoreGenerator(hostFile);
            } else {
                gen = new KeystoreGenerator(privateKeyFile, DEFAULT_PASSWORD, caCert, hostFile);

            }

        }
        //read in the private key from a file
        else {
            String privateKeyFile = (String) opts.valueOf(PRIVATE_KEY_FILE_OPT);
            String x509CertFile = (String) opts.valueOf(CERTIFICATE_FILE_OPT);
            String password = (String) opts.valueOf(PEM_PASSWORD_OPT);


            gen = new KeystoreGenerator(privateKeyFile, password, x509CertFile, hostFile);

        }

        for (String hostname : gen.getHosts()) {
            gen.buildKeyStore(hostname);
        }

    }

    /**
     * This constructor generates our own certificate and private key
     *
     * @throws Exception
     */
    public KeystoreGenerator(String hostsFile) throws Exception {

        this();
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(2048, new SecureRandom());
        caKeypair = kpGen.generateKeyPair();

        //this looks weird because we're creating a self signed cert for our root cert
        caCert = generateCert("ca", caKeypair.getPublic(), caKeypair.getPrivate(), null);
        this.hosts = readHostsFromInputFile(new File(hostsFile));
    }

    public KeystoreGenerator() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    /**
     * @param keyPairFile
     * @param keyPairPassword
     * @param x509CertFile
     * @throws Exception
     */
    public KeystoreGenerator(String keyPairFile,
                             String keyPairPassword,
                             String x509CertFile,
                             String hostFile) throws Exception {
        this();
        if (keyPairFile == null) {
            log.fatal("Must specify private key file");
        }

        if (x509CertFile == null) {
            log.fatal("Must specify x509 cert file");
        }


        File privateKeyFileHandle = new File(keyPairFile);
        if (!privateKeyFileHandle.canRead()) {
            throw new Exception("Can't read the privateKeyFile");
        }

        File x509CertFileHandle = new File(x509CertFile);
        if (!x509CertFileHandle.canRead()) {
            throw new Exception("Can't read certificate file");
        }
        this.caCert = readCertFromFile(x509CertFileHandle);
        this.caKeypair = readKeyPair(privateKeyFileHandle, keyPairPassword);
        this.hosts = readHostsFromInputFile(new File(hostFile));

    }

    /**
     * 1q
     *
     * @param hostname
     * @throws Exception
     */
    public void buildKeyStore(final String hostname)
            throws Exception {

        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(null, password.toCharArray());

        if (caCert == null) {
            throw new Exception("Must initialize the CA's public/private keys first");
        }

        //first thing we do is plop in the CA's public key
        ks.setCertificateEntry("ca", caCert);


        KeyPair nodeKeyPair = generateKeyPair();
        //Generate a new cert and sign it with the signer key


        X509Certificate nodeCert = generateCert(hostname, nodeKeyPair.getPublic(), caKeypair.getPrivate(), caCert);

        ks.setKeyEntry("mykey", nodeKeyPair.getPrivate(), password.toCharArray(), new Certificate[]{nodeCert, caCert});

        File outputFile = new File(hostname + "-keystore.jks");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        FileOutputStream out = new FileOutputStream(outputFile);
        ks.store(out, password.toCharArray());


    }

    /**
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    protected KeyPair generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(2048, new SecureRandom());
        KeyPair pair = kpGen.generateKeyPair();
        return pair;
    }


    /**
     * @param name
     * @param myPublicKey
     * @param signerKey
     * @param signerCert
     * @return
     * @throws Exception
     */
    public X509Certificate generateCert(String name, PublicKey myPublicKey,
                                        PrivateKey signerKey, X509Certificate signerCert) throws Exception {
        Date startDate = new Date();
        Date expiryDate;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        expiryDate = cal.getTime();

        BigInteger serialNumber = new BigInteger(64, new Random());

        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        X509Principal dnName = new X509Principal("CN=" + name);


        if (signerCert == null) {
            certGen.setIssuerDN(dnName);
        } else {
            certGen.setIssuerDN(signerCert.getIssuerX500Principal());
        }

        certGen.setSerialNumber(serialNumber);
        certGen.setNotBefore(startDate);
        certGen.setNotAfter(expiryDate);
        certGen.setSubjectDN(dnName);                       // note: same as issuer
        certGen.setPublicKey(myPublicKey);
        certGen.setSignatureAlgorithm("SHA256withRSA");

        if (signerCert != null) {
            certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
                    new AuthorityKeyIdentifierStructure(signerCert));
        }

        certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
                new SubjectKeyIdentifierStructure(myPublicKey)

        );

        X509Certificate returnCert = certGen.generate(signerKey, "BC");

        return returnCert;
    }

    /**
     * @param file handle of the certificate
     * @return x509 certificate
     * @throws Exception anything
     */
    public static X509Certificate readCertFromFile(File file) throws Exception {

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
        FileInputStream fstream = new FileInputStream(file);
        return (X509Certificate) certificateFactory.generateCertificate(fstream);


    }

    private static class DefaultPasswordFinder implements PasswordFinder {
        private final char[] password;

        private DefaultPasswordFinder(char[] password) {
            this.password = Arrays.copyOf(password, password.length);
        }

        @Override
        public char[] getPassword() {
            return Arrays.copyOf(password, password.length);
        }
    }

    /**
     * @param privateKey
     * @param keyPasswordString
     * @return
     * @throws IOException
     */
    public static KeyPair readKeyPair(File privateKey, String keyPasswordString) throws IOException {
        FileReader fileReader = new FileReader(privateKey);
        PEMReader r;
        char[] keyPassword = null;

        if(keyPasswordString != null ){
            keyPassword = keyPasswordString.toCharArray();
        }

        if(keyPassword != null){
               r = new PEMReader(fileReader, new DefaultPasswordFinder(keyPassword));
        }
        else{
            r = new PEMReader(fileReader);
        }
        try {
            return (KeyPair) r.readObject();
        } catch (IOException ex) {
            throw new IOException("The private key could not be decrypted", ex);
        } finally {
            r.close();
            fileReader.close();
        }
    }

    public static List<String> readHostsFromInputFile(File file) throws Exception {
        if (!file.canRead()) {
            throw new Exception("Can't read the hosts input file");
        }

        List<String> returnList = new ArrayList<String>();
        FileInputStream fin = new FileInputStream(file);
        DataInputStream inputStream = new DataInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String strLine;

        while ((strLine = br.readLine()) != null && strLine.length() > 0) {
            returnList.add(strLine.trim());
        }
        return returnList;
    }


}

