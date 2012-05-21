package edu.chip.carranet.auth.backend;

import edu.chip.carranet.jaxb.CarraUserInfo;
import org.apache.log4j.Logger;
import org.restlet.engine.util.Base64;
import org.spin.tools.JAXBUtils;
import org.spin.tools.NetworkTime;
import org.spin.tools.config.ConfigException;
import org.spin.tools.crypto.signature.Identity;
import org.spin.tools.crypto.signature.XMLSignatureUtil;

import javax.xml.bind.JAXBException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class TokenUtil {

    private static final Logger log = Logger.getLogger(TokenUtil.class);
    private static final int TOKEN_TTL_MINUTES = 3;


    /**
     * <p>
     * this will sign a CarraToken
     * </p>
     *
     * @return a signed CarraToken
     */
    public static Identity createToken(final CarraUserInfo user) {

        Identity returnIdentity = new Identity("carra", user.getUsername());
        for (String s : user.getAssertions()) {
            returnIdentity.addAssertion(s);
        }

        try {

            //attempt to sign the identity
            //if I can't get the XMLSignatureUtil.sign method to work
            //I'll have to rewrite it, update tests.
            returnIdentity = XMLSignatureUtil.getDefaultInstance().sign(returnIdentity, Identity.class);
            return returnIdentity;


        } catch (XMLSignatureException e) {
            throw new RuntimeException("Error Signing Identity", e);
        } catch (ConfigException e) {
            throw new RuntimeException("Error configuring SPIN PKI Tool");
        }
    }

    /**
     * <p>
     * Returns the Idenity object reperesenting by the given edu.chip.carranet.auth string,
     * will return null if the string isn't an ID
     * </p>
     *
     * @param authString
     * @return
     */
    public static Identity authStringToIdentity(String authString) {
        Identity id = null;
        if (authString == null) {
            return null;
        }
        try {
            String decodedIdString = new String(Base64.decode(authString), "UTF-8");
            log.debug("Decoding Authstring " + decodedIdString);
            id = JAXBUtils.unmarshal(decodedIdString, Identity.class);
        } catch (JAXBException e) {
            log.fatal("Error unmarshalling");
            return null;
        } catch (UnsupportedEncodingException e) {
            log.fatal("Unsupported Encoding");
            return null;
        }


        return id;

    }


    /**
     * <p>
     * Returns true if if the token was signed
     * by this server
     * </p>
     *
     * @return
     */
    public static boolean verifyToken(Identity token) {
        boolean identityGood = false;

        try {
            identityGood = XMLSignatureUtil.getDefaultInstance().verifySignature(token);
        } catch (Exception e) {
            log.error("Bad token for user " + token.getUsername());
            return false;
        }

        XMLGregorianCalendar currentTime = generateCurrentXmlDate();

        return identityGood &&
                (currentTime.compare(token.getTimestamp()) >= 0);

    }


    /**
     * <p>
     * Returns true if if the token was signed
     * by this server
     * </p>
     *
     * @return
     */
    public static boolean verifyTokenString(String token) {

        try {
            return verifyToken(convertAuthToIdentity(token));
        } catch (JAXBException e) {
            log.error("JAXBException converting edu.chip.carranet.auth string to token returning false");
        } catch (UnsupportedEncodingException e) {
            log.error("Token String encoding is incorrect, use only UTF-8");
        }
        return false;
    }


    /**
     * Create an XMLGregorian Calendar with a time of now + TOKEN_TTL_MINUTES
     *
     * @return
     */
    private static XMLGregorianCalendar generateTokenExpirationDate() {
        //Create a timestamp TOKEN_TTL_MINUTES minutes in the future
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MINUTE, (cal.get(Calendar.MINUTE) + TOKEN_TTL_MINUTES));
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(cal.getTime());

        XMLGregorianCalendar xmlDate;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            log.fatal("Error Creating XML dates");
            throw new RuntimeException("fatal error, can't create XML date objects, can't timestamp tokens");
        }

        return xmlDate;
    }

    /**
     * Just create an XMLGregorianCalendar with the current
     * date and time
     *
     * @return
     */
    private static XMLGregorianCalendar generateCurrentXmlDate() {
        //Create a timestamp TOKEN_TTL_MINUTES minutes in the future
        return new NetworkTime().getXMLGregorianCalendar();

    }


    public static String IdentityToString(Identity id) throws JAXBException, UnsupportedEncodingException {


        String returnString = JAXBUtils.marshalToString(id);
        return Base64.encode(returnString.getBytes("UTF-8"), false);
    }

    /**
     * Convert the authorization string in the http header
     * to an Identity Object
     *
     * @param authHeaderString
     * @return
     */
    public static Identity convertAuthToIdentity(String authHeaderString)
            throws JAXBException, UnsupportedEncodingException {


        byte[] byteArray = Base64.decode(authHeaderString);
        String xmlIdentity = new String(byteArray, "UTF-8");
        return JAXBUtils.unmarshal(xmlIdentity, Identity.class);
    }

    public static String convertIdentityToAuthString(Identity e)
            throws JAXBException, UnsupportedEncodingException {

        String idString = JAXBUtils.marshalToString(e);

        String returnString = Base64.encode(idString.getBytes("UTF-8"), false);
        return returnString;
    }


    /**
     * Takes the authorization header, base64 decodes it with yeilds
     * username:password tokenizes it and runs it against the authenticator.
     * <p/>
     * The getToken method is the only call that uses basic edu.chip.carranet.auth, every other
     * call requires a token
     *
     * @return A userpair or null for an improperly formatted string
     */
    public static User userFromHttpBasicAuth(final String username, final String password) {

//        if (basicAuthString == null) {
//            return null;
//        }
//
//
//        String decodedString = "";
//        try {
//            decodedString = new String(Base64.decode(basicAuthString), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            log.fatal("String encoding error, expecting only UTF-8 strings");
//
//        }


//        String[] tokenizedStrings = decodedString.split(":");
//        if (tokenizedStrings.length < 2) {
//            return null;
//        }
//
//        if (tokenizedStrings[0] == null ||
//                tokenizedStrings[1] == null) {
//
//            return null;
//        }

        return new User(username, password);

    }

    public static String getAuthString(Identity id) {
        String xmlString = "";
        try {
            xmlString = JAXBUtils.marshalToString(id);
        } catch (JAXBException e) {
            log.fatal("Error unmarshalling ID, shouldn't happen");
        }

        String returnString = Base64.encode(xmlString.getBytes(), false);
        return returnString;
    }


}
