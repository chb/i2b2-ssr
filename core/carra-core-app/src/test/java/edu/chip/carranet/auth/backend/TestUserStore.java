package edu.chip.carranet.auth.backend;

import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class TestUserStore {


    public static final String TEST_USERNAME = "test";
    public static final String TEST_PASSWORD = "test";

    @Resource(name = "userStore")
    private IUserStore testUserStore;

    @Resource
    IAuthenticator authenticator;


    @Before
    @Rollback(false)
    @Transactional
    public void setup() throws Exception {
        testUserStore.addUser(null, TEST_USERNAME, TEST_PASSWORD);

    }


    /**
     * Test if authenticating a user actually gives us back the correct
     * token
     */
    @Test(expected = AuthorizationFailedError.class)
    @Rollback(true)
    public void testUserAuth() throws AuthException {
        //try the good user
        Identity returnIdentity = authenticator.authenticate(TEST_USERNAME, TEST_PASSWORD);
        assertNotNull(returnIdentity);

        returnIdentity = authenticator.authenticate("junk", "junk");
        assertEquals(null, returnIdentity);
    }

    /**
     * Test adding a user with a good token
     */
    @Test
    @Rollback(true)
    public void testAddUser() throws AuthException {

        Identity identity = authenticator.authenticate(TEST_USERNAME, TEST_PASSWORD);

        testUserStore.addUser(identity, "newuser1", "newuser1");
        assertNotNull(findUser(testUserStore.getUsers(identity), "newuser1"));


    }


    /**
     * Get a bad token and try to add a user, it should fail
     */
    @Test(expected = AuthorizationFailedError.class)
    @Rollback(true)
    public void testAddUserBadToken() {
        Identity identity = new Identity();
        testUserStore.addUser(identity, "newuser1", "newuser1");


    }

    @Test
    @Rollback(true)
    public void testAddUserRole() {

    }


    @Test
    @Rollback(true)
    public void testDeleteUser() throws AuthException {

        Identity identity = authenticator.authenticate(TEST_USERNAME, TEST_PASSWORD);


        testUserStore.addUser(identity, "newuser2", "newuser2");
        assertNotNull(findUser(testUserStore.getUsers(identity), "newuser2"));


        testUserStore.deleteUser(identity, "newuser2");
        assertNull(findUser(testUserStore.getUsers(identity), "newuser2"));
    }


    @Test(expected = AuthorizationFailedError.class)
    @Rollback(true)
    public void testDeleteUserBadToken() throws AuthException {
        Identity identity = new Identity(null, null);
        testUserStore.deleteUser(identity, "newuser1");
        assertNull(findUser(testUserStore.getUsers(identity), "newuser1"));
    }


    private CarraUserInfo findUser(List<CarraUserInfo> list, String username) {

        for (CarraUserInfo n : list) {
            if (n.getUsername().equals(username)) {
                return n;
            }

        }
        return null;
    }
}
