package edu.chip.carranet.auth;


import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import edu.chip.carranet.jaxb.CarraUser;
import edu.chip.carranet.jaxb.CarraUserInfo;
import edu.chip.carranet.jaxb.CarraUsers;
import edu.chip.carranet.jaxb.CreateUserMessage;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.spin.tools.JAXBUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.context.ContextLoaderListener;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * This class test will test the rest endpoints when I implement them
 */

public class TestAuthApplication extends JerseyTest {

    private Logger log = Logger.getLogger(TestAuthApplication.class);
    private ClientResource userListResource = new ClientResource(
            "http://localhost:9998/test/users/");


    public TestAuthApplication() {
        super(new WebAppDescriptor.Builder("edu.chip.carranet")
                .contextPath("/test")
                .contextParam("contextConfigLocation", "/testApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());
    }

    @Before
    public void setupContext() throws Exception {
        addUser(null, null, "dave", "test");
    }

    @After
    public void resetContext() throws Exception {
        deleteUser("dave", "test", "dave");
    }

    @Test
    @Rollback
    public void testGetUser() throws Exception {
        ClientResource resource = new ClientResource(
                "http://localhost:9998/test/users/dave");

        String authString = "";
        authString = getAuthToken("dave", "test");

        Form f = new Form();
        f.add("x-carra-auth", authString);
        resource.getRequestAttributes().put("org.restlet.http.headers", f);
        CarraUser user = JAXBUtils.unmarshal(resource.get().getText(), CarraUser.class);

        user.getUser().getAssertions().add("HI");
        resource.put(new StringRepresentation(JAXBUtils.marshalToString(user)));

        user = JAXBUtils.unmarshal(resource.get().getText(), CarraUser.class);
        resource.release();

        assertEquals(true, user.getUser().getAssertions().contains("HI"));


    }

    private String getAuthToken(String userName, String password) throws Exception {
        ClientResource tokenResource = generateUserTokenResource(userName);
        Form f = new Form();
        f.add("username", userName);
        f.add("password", password);
        tokenResource.getRequestAttributes().put("org.restlet.http.headers", f);

        Representation r = tokenResource.get();
        String s = r.getText();
        r.release();

        return s;
    }

    private ClientResource generateUserTokenResource(String userName) {
        userName = userName.trim();

        ClientResource returnVal = new ClientResource(
                "http://localhost:9998/test/users/" + userName + "/authorization_token");
        return returnVal;
    }


    @Test
    public void testAddDeleteUsers() throws Exception {
        String authString = getAuthToken("dave", "test");

        Form f = new Form();
        f.add("x-carra-auth", authString);
        userListResource.getRequestAttributes().put("org.restlet.http.headers", f);
        String carraUserList = userListResource.get().getText();
        CarraUsers userList = JAXBUtils.unmarshal(carraUserList, CarraUsers.class);

        assertEquals(true, userExists(userList.getUsers(), "dave"));


        addUser("dave", "test", "justin", "testPassword2");
        carraUserList = userListResource.get().getText();
        userList = JAXBUtils.unmarshal(carraUserList, CarraUsers.class);
        assertEquals(true, userExists(userList.getUsers(), "justin"));


        //FIX-ME
        //this doesn't make sense anymore, fix test

        addUser("dave", "test", "marc", "thenatternator");
        carraUserList = userListResource.get().getText();
        userList = JAXBUtils.unmarshal(carraUserList, CarraUsers.class);
        assertEquals(true, userExists(userList.getUsers(), "justin"));

        deleteUser("dave", "test", "marc");
        carraUserList = userListResource.get().getText();
        userList = JAXBUtils.unmarshal(carraUserList, CarraUsers.class);
        assertEquals(false, userExists(userList.getUsers(), "marc"));

        deleteUser("dave", "test", "justin");
        carraUserList = userListResource.get().getText();
        userList = JAXBUtils.unmarshal(carraUserList, CarraUsers.class);
        assertEquals(false, userExists(userList.getUsers(), "justin"));

    }

    private void addUser(String yourUserName, String yourUserPassword,
                         String newUserName, String password) throws Exception {
        String addUserMessage =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<ns2:CarraUsers xmlns:ns2=\"http://carranet.edu/CreateUserMessage\">\n" +
                        "    <ns2:userName>" + newUserName + "</ns2:userName>\n" +
                        "    <ns2:password>" + password + "</ns2:password>\n" +
                        "</ns2:CarraUsers>";


        String authString = "";
        ClientResource userAddJustResource = new ClientResource(
                "http://localhost:9998/test/users");

        if (yourUserName != null && yourUserPassword != null) {
            authString = getAuthToken(yourUserName, yourUserPassword);
            Form f = new Form();
            f.add("x-carra-auth", authString);
            userAddJustResource.getRequestAttributes().put("org.restlet.http.headers", f);
        }


        Representation message = new StringRepresentation(addUserMessage);
        message.setMediaType(MediaType.TEXT_XML);
        userAddJustResource.post(message);


    }

    private boolean userExists(List<CarraUserInfo> list, String name) {
        for (CarraUserInfo i : list) {
            if (i.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * this should cause a 404
     */
    @Test
    @Rollback
    public void testBadId() throws Exception {
        ClientResource userPutResource = new ClientResource(
                "http://localhost:9998/test/users/" + "dave" + "/");

        String authString = "BADSTRNG";


        CreateUserMessage m = new CreateUserMessage();
        m.setUserName("dave");
        m.setPassword("badPass");

        Representation message = new StringRepresentation(JAXBUtils.marshalToString(m));


        Form f = new Form();
        f.add("x-carra-auth", authString);
        userPutResource.getRequestAttributes().put("org.restlet.http.headers", f);
        try {
            userPutResource.delete();

        } catch (ResourceException e) {
            log.info("server responded with " + e.getMessage());
        }
        Status s = userPutResource.getStatus();
        assertEquals(Status.CLIENT_ERROR_FORBIDDEN, s);
    }

    private void deleteUser(String yourUserName, String yourUserPassword, String userToDelete)
            throws Exception {
        ClientResource userDelResource = new ClientResource(
                "http://localhost:9998/test/users/" + userToDelete);

        String authString = "";
        if (yourUserName != null && yourUserPassword != null) {
            authString = getAuthToken(yourUserName, yourUserPassword);
        }


        Form f = new Form();
        f.add("x-carra-auth", authString);
        userDelResource.getRequestAttributes().put("org.restlet.http.headers", f);

        userDelResource.delete();
    }

    @Test
    public void testHealthCheck() throws Exception {
        ClientResource healthCheckResource = new ClientResource("http://localhost:9998/test/integration/healthcheck");
        Representation r = healthCheckResource.get();
        assertEquals("Healthy", r.getText());

    }
}
