package edu.chip.carranet.auth.backend;

import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.junit.Test;
import org.restlet.engine.util.Base64;
import org.spin.tools.JAXBUtils;
import org.spin.tools.crypto.signature.Identity;

import static org.junit.Assert.assertEquals;


/**
 * Test cases for the TokenUtil class
 * which
 */
public class TestTokenUtil {


    @Test
    public void testCreateToken() throws AuthException {

        CarraUserInfo testUserInfo = new CarraUserInfo();
        testUserInfo.setUsername("Dave");
        Identity id = TokenUtil.createToken(testUserInfo);
        assertEquals(true, TokenUtil.verifyToken(id));

    }


    /**
     * Test if if you can verify a good token
     *
     * @throws AuthException
     */
    @Test
    public void testVerifyGoodToken() throws AuthException {
        CarraUserInfo testUserInfo = new CarraUserInfo();

        testUserInfo.setUsername("Dave");


        Identity id = TokenUtil.createToken(testUserInfo);
        assertEquals(true, TokenUtil.verifyToken(id));
    }


    /**
     * Test if you can verify a bad token
     * should throw an AuthException
     *
     * @throws AuthException
     */
    @Test
    public void testVerifyBadToken() throws AuthException {
        CarraUserInfo testUserInfo = new CarraUserInfo();

        testUserInfo.setUsername("Dave");

        Identity id = new Identity();

        //Should fail obviously
        assertEquals(false, TokenUtil.verifyToken(id));

    }


    @Test
    public void testIdentityToString() throws Exception {
        Identity id1 = new Identity("none", "Dave");
        Identity id2 = new Identity("none", "Justin");

        String s1 = TokenUtil.convertIdentityToAuthString(id1);
        String s2 = TokenUtil.convertIdentityToAuthString(id1);

        String finals1 = new String(Base64.decode(s1), "UTF-8");
        String finals2 = new String(Base64.decode(s2), "UTF-8");

        Identity finalId1 = JAXBUtils.unmarshal(finals1, Identity.class);
        Identity finalId2 = JAXBUtils.unmarshal(finals2, Identity.class);


        assertEquals(finalId1.getDomain(), id1.getDomain());
        assertEquals(finalId1.getId(), id1.getId());

        assertEquals(finalId2.getDomain(), id2.getDomain());
        assertEquals(finalId2.getId(), id2.getId());

    }


    @Test
    public void TestUserFromHttpBasicAuth() throws Exception {

        String user1 = "dave:password";
        String user2 = "justin:password2";


        String encodedUser1 = Base64.encode(user1.getBytes("UTF-8"), false);
        String encodedUser2 = Base64.encode(user2.getBytes("UTF-8"), false);

        String badString = null;

        User response1 = TokenUtil.userFromHttpBasicAuth("dave","password");
        User response2 = TokenUtil.userFromHttpBasicAuth("justin","password2");


        //Just make sure these don't throw exceptions
        TokenUtil.userFromHttpBasicAuth("","");
        TokenUtil.userFromHttpBasicAuth(null, null);

        assertEquals("dave", response1.getUserName());
        assertEquals("password", response1.getHashedPassword());

        assertEquals("justin", response2.getUserName());
        assertEquals("password2", response2.getHashedPassword());
    }

    @Test
    public void testConvertAuthToIdentity() throws Exception {


        Identity id1 = new Identity("nodomain", "dave");
        Identity id2 = new Identity("nodomain", "user2");

        String xmlId1 = JAXBUtils.marshalToString(id1);
        String xmlId2 = JAXBUtils.marshalToString(id2);

        String authHeader1 = Base64.encode(xmlId1.getBytes("UTF-8"), false);
        String authHeader2 = Base64.encode(xmlId2.getBytes("UTF-8"), false);


        Identity idAfter1 = TokenUtil.convertAuthToIdentity(authHeader1);
        Identity idAfter2 = TokenUtil.convertAuthToIdentity(authHeader2);

        assertEquals(id1.getDomain(), "nodomain");
        assertEquals(id1.getUsername(), "dave");

        assertEquals(id2.getDomain(), "nodomain");
        assertEquals(id2.getUsername(), "user2");


    }


}
