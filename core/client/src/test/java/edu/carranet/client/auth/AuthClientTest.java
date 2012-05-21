package edu.carranet.client.auth;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import edu.chip.carranet.auth.backend.UserPermissions;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.context.ContextLoaderListener;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for edu.chip.carranet.auth client implementations
 */
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class AuthClientTest extends JerseyTest {


    private IAuthClient client;


    public AuthClientTest() throws Exception {
        super(new WebAppDescriptor.Builder("edu.chip.carranet")
                .contextPath("/test")
                .contextParam("contextConfigLocation", "/testApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());

        client = new RestletAuthClient("http://localhost:9998/test/");
    }


    @Before
    public void setupData() throws Exception {
        client.addUser(null, "dave", "test");
    }

    @After
    public void unsetupData() throws Exception {
        Token t = client.getAuthorization("dave", "test");
        client.deleteUser(t, "dave");

    }


    @Test
    public void testAddDeleteUsers() throws Exception {
        Token t = client.getAuthorization("dave", "test");


        client.addUser(t, "justin", "testPassword2");
        List<CarraUserInfo> carraUserList = client.getUsers(t);
        assertEquals(true, userExists(carraUserList, "justin"));


        t = client.getAuthorization("justin", "testPassword2");
        assertNotNull(t);
        t = client.getAuthorization("dave", "test");
        client.addUser(t, "marc", "thenatternator");
        carraUserList = client.getUsers(t);
        assertEquals(true, userExists(carraUserList, "justin"));

        t = client.getAuthorization("dave", "test");
        client.deleteUser(t, "justin");
        client.deleteUser(t, "marc");


    }


    private boolean userExists(List<CarraUserInfo> list, String name) {

        for (CarraUserInfo i : list) {
            if (i.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }


//    @Test
//    @Ignore
//    public void testChangeUserPW() throws Exception {
//
//        Token t = client.getAuthorization("dave", "test");
//
//
//        client.addUser(t, "testuser", "test");
//
//        t = client.getAuthorization("testuser", "test");
//
//        client.changeUserPassword(t, "testuser", "test", "newPass");
//
//        t = client.getAuthorization("testuser", "newPass");
//
//        t = client.getAuthorization("dave", "test");
//
//        client.deleteUser(t, "testuser");
//
//
//    }


    @Test
    public void testGetUserRoles() throws Exception {
        Token t = client.getAuthorization("dave", "test");
        List<UserPermissions> roles = new ArrayList<UserPermissions>();
        roles.add(UserPermissions.pdo);
        roles.add(UserPermissions.sitebreakdown);

        client.addRolesToUser(t, "dave", roles);

        List<UserPermissions> rolesAfter = client.getUserRoles(t, "dave");
        assertTrue(rolesAfter.contains(UserPermissions.pdo));
        assertTrue(rolesAfter.contains(UserPermissions.sitebreakdown));

        client.removeUserRoles(t, "dave", roles);
        rolesAfter = client.getUserRoles(t, "dave");
        assertFalse(rolesAfter.contains(UserPermissions.pdo));
        assertFalse(rolesAfter.contains(UserPermissions.sitebreakdown));

    }

    @Test
    public void testGetUserStudies() throws Exception {
        Token t = client.getAuthorization("dave", "test");
        List<String> studies = new ArrayList<String>();
        studies.add("test1");
        studies.add("test2");

        client.addUserToStudies(t, "dave", studies);

        List<String> studiesAfter = client.getUserStudies(t, "dave");
        assertEquals(true, studiesAfter.contains("test1"));
        assertEquals(true, studiesAfter.contains("test2"));

        client.removeUserFromStudies(t, "dave", studies);
        studiesAfter = client.getUserStudies(t, "dave");
        assertEquals(false, studiesAfter.contains("test1"));
        assertEquals(false, studiesAfter.contains("test2"));

    }

//        @Test
//        @Ignore
//        public void testGetCapabilities() throws Exception {
//            Token t = client.getAuthorization("dave", "test");
//            AuthCapabilities capabilities = client.getCapabilities(t);
//
//            assertEquals(true, capabilities.getCapability().contains(AuthCapabilityEnum.CHANGE_PASSWORD.getStringValue()));
//
//    }

    @Test
    public void testSetUserRolesAndStudies() throws Exception {
        Token t = client.getAuthorization("dave", "test");

        List<String> studies = new ArrayList<String>();
        studies.add("test1");
        studies.add("test2");

        List<UserPermissions> roles = new ArrayList<UserPermissions>();
        roles.add(UserPermissions.admin);
        roles.add(UserPermissions.pdo);

        CarraUserInfo info = client.getUserInfo(t, "dave");

        info.getAssertions().add("study:test1");
        info.getAssertions().add("study:test2");

        for (UserPermissions role : roles) {
            info.getAssertions().add(role.toAssertionString());
        }

        client.updateUser(t, "dave", info);

        List<String> studiesRusults = client.getUserStudies(t, "dave");
        List<UserPermissions> rolesResults = client.getUserRoles(t, "dave");

        assertTrue(studiesRusults.contains("test1"));
        assertTrue(studiesRusults.contains("test2"));

        assertTrue(rolesResults.contains(UserPermissions.admin));
        assertTrue(rolesResults.contains(UserPermissions.pdo));


    }


}
