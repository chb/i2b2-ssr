package edu.carranet.client.auth;

import edu.carranet.client.exception.AuthClientException;
import edu.carranet.client.exception.PermissionException;
import edu.carranet.client.exception.ResourceNotFoundException;
import edu.carranet.client.exception.ServerException;
import edu.chip.carranet.auth.backend.UserPermissions;
import edu.chip.carranet.auth.backend.UserUtil;
import edu.chip.carranet.jaxb.*;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.util.Base64;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.spin.tools.JAXBUtils;
import org.spin.tools.crypto.signature.Identity;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Implementation of the Auth client using the restlet client side libraries.
 * Look into apache HttpClient version some time in the future
 */
public class RestletAuthClient implements IAuthClient {


    private URL endpointPrefix = null;
    private static String STUDY_PREFIX = "study";


    /**
     * <p>
     * Create a restlet AuthClient
     * <p/>
     * <p/>
     * Prefix example
     * </p>
     * "http://carranet.edu/
     * "http://localhost:8182/restlet_servlet
     *
     * @param endpointPrefix prefex for the endpoint
     * @throws MalformedURLException when the endpoint prefix isn't a valid URL path
     */
    public RestletAuthClient(String endpointPrefix) throws MalformedURLException {

        this.endpointPrefix = new URL(endpointPrefix);
    }

    @Override
    public void addUser(Token t, String userName, String password)
            throws AuthClientException {

        CreateUserMessage newUserMessage = new CreateUserMessage();
        newUserMessage.setUserName(userName);
        newUserMessage.setPassword(password);

        ClientResource userAddResource = new ClientResource(
                endpointPrefix.toExternalForm() + "users");

        try {

            if (t != null) {
                Form f = new Form();
                f.add("x-carra-auth", t.getAuthString());
                userAddResource.getRequestAttributes().put("org.restlet.http.headers", f);
            }
            try {
                Representation message = new StringRepresentation(JAXBUtils.marshalToString(newUserMessage));
                message.setMediaType(MediaType.TEXT_XML);
                userAddResource.post(message);
            } catch (JAXBException e) {
                throw new AuthClientException("Error creating message on the client, file a bug");
            } catch (ResourceException e) {
                throw new AuthClientException("Unknown error");
            }

            checkResponse(userAddResource.getStatus());
        } finally {
            if (userAddResource != null) {
                userAddResource.release();
            }
        }

    }


    @Override
    public void deleteUser(Token t, String userName)
            throws AuthClientException {


        ClientResource userDelJustResource = new ClientResource(
                endpointPrefix.toExternalForm() + "users/" + userName + "/");

        try {
            Form f = new Form();
            f.add("x-carra-auth", t.getAuthString());
            userDelJustResource.getRequestAttributes().put("org.restlet.http.headers", f);

            try {
                userDelJustResource.delete();
            } catch (ResourceException e) {
                throw new AuthClientException("Unknown error");
            }

            checkResponse(userDelJustResource.getStatus());
        } finally {
            userDelJustResource.release();
        }

    }

    @Override
    public void changeUserPassword(Token t, String userName, String oldPassword, String newPassword)
            throws AuthClientException {

        ChangePasswordMessage changePwMessage = new ChangePasswordMessage();
        changePwMessage.setNewPassword(newPassword);
        changePwMessage.setOldPassword(oldPassword);


        ClientResource userAddResource = new ClientResource(
                endpointPrefix.toExternalForm() + "users/" + userName + "/password");

        try {
            Form f = new Form();
            f.add("x-carra-auth", t.getAuthString());
            userAddResource.getRequestAttributes().put("org.restlet.http.headers", f);

            try {
                Representation message = new StringRepresentation(JAXBUtils.marshalToString(changePwMessage));
                message.setMediaType(MediaType.TEXT_XML);
                userAddResource.put(message);
            } catch (JAXBException e) {
                throw new AuthClientException("Error creating message on the client, file a bug");
            } catch (ResourceException e) {
                throw new AuthClientException("Unknown error");
            }

            checkResponse(userAddResource.getStatus());
        } finally {
            userAddResource.release();
        }

    }


    @Override
    public Token getAuthorization(String userName, String password) throws AuthClientException {
        ClientResource tokenResource = generateUserTokenResource(userName);

        try {
            Form f = new Form();
            f.add("username", userName);
            f.add("password", password);
            tokenResource.getRequestAttributes().put("org.restlet.http.headers", f);

            Representation r = tokenResource.get();


            String s = r.getText();
            r.release();
            if (tokenResource.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)) {
                throw new PermissionException("Not authorized to get token, bad username/pw");
            }

            checkResponse(tokenResource.getStatus());
            Identity id;

            if (s == null) {
                throw new PermissionException("Couldn't authenticate");
            }

            s = new String(Base64.decode(s), "UTF-8");

            id = JAXBUtils.unmarshal(s, Identity.class);
            Token returnToken = new Token(id);
            return returnToken;
        } catch (JAXBException e) {
            throw new AuthClientException("Unexpected error unmarshalling response");
        } catch (UnsupportedEncodingException e) {
            throw new AuthClientException("Unexpected error, server response contained unexpected encoding");
        } catch (ResourceException e) {
            throw new AuthClientException("Server resource expception");
        } catch (IOException e) {
            throw new AuthClientException("IO Error in getAuthorization()");
        } finally {
            tokenResource.release();
        }


    }

    public CarraUserInfo getUserInfo(Token t, String userName) throws AuthClientException {


        ClientResource userAddResource = new ClientResource(
                endpointPrefix.toExternalForm() + "users/" + userName + "/");

        try {
            if (t != null) {
                Form f = new Form();
                f.add("x-carra-auth", t.getAuthString());
                userAddResource.getRequestAttributes().put("org.restlet.http.headers", f);
            }
            try {

                String xmlString = userAddResource.get().getText();
                CarraUser user = JAXBUtils.unmarshal(xmlString, CarraUser.class);
                checkResponse(userAddResource.getStatus());
                return user.getUser();
            } catch (JAXBException e) {
                throw new AuthClientException("Error creating message on the client, file a bug");
            } catch (ResourceException e) {
                throw new AuthClientException("Unknown error");
            } catch (IOException e) {
                throw new AuthClientException("I/O error");
            }
        } finally {
            userAddResource.release();
        }


    }

    public void updateUser(Token t, String userName, CarraUserInfo u) throws AuthClientException {

        ClientResource resource = new ClientResource(
                endpointPrefix.toExternalForm() + "users/" + userName + "/");

        try {
            if (t != null) {
                Form f = new Form();
                f.add("x-carra-auth", t.getAuthString());
                resource.getRequestAttributes().put("org.restlet.http.headers", f);
            }
            try {
                CarraUser user = new CarraUser();
                user.setUser(u);
                resource.put(new StringRepresentation(JAXBUtils.marshalToString(user)));
                checkResponse(resource.getStatus());

            } catch (JAXBException e) {
                throw new AuthClientException("Error creating message on the client, file a bug");
            } catch (ResourceException e) {
                throw new AuthClientException("Unknown error");
            }
        } finally {
            resource.release();
        }
    }


    @Override
    public List<String> getUserStudies(Token t, String userName) throws AuthClientException {
        CarraUserInfo info = getUserInfo(t, userName);
        return UserUtil.getStudiesFromUser(info);
    }


    @Override
    public void addUserToStudies(Token t, String userName, List<String> studyNames) throws AuthClientException {
        CarraUserInfo info = getUserInfo(t, userName);

        //We don't want to add duplicates
        Set<String> assertions = new HashSet<String>();
        assertions.addAll(info.getAssertions());

        for (String s : studyNames) {
            assertions.add(STUDY_PREFIX + ":" + s);
        }

        info.getAssertions().clear();
        info.getAssertions().addAll(assertions);

        //send the new CarraUserInfo
        updateUser(t, userName, info);

    }

    @Override
    public void removeUserFromStudies(Token t, String userName, List<String> studyNames) throws AuthClientException {
        CarraUserInfo info = getUserInfo(t, userName);

        for (String s : studyNames) {
            if (info.getAssertions().contains(STUDY_PREFIX + ":" + s)) {
                info.getAssertions().remove(STUDY_PREFIX + ":" + s);
            }
        }
        updateUser(t, userName, info);
    }

    @Override
    public List<UserPermissions> getUserRoles(Token t, String userName) throws AuthClientException {
        CarraUserInfo info = getUserInfo(t, userName);
        return UserUtil.getsRolesFromUser(info);
    }

    @Override
    public void addRolesToUser(Token t, String userName, List<UserPermissions> roles) throws AuthClientException {
        CarraUserInfo info = getUserInfo(t, userName);

        //We don't want to add duplicates
        Set<String> assertions = new HashSet<String>();
        assertions.addAll(info.getAssertions());
        for (UserPermissions role : roles) {
            assertions.add(role.toAssertionString());
        }

        info.getAssertions().clear();
        info.getAssertions().addAll(assertions);

        //send the new CarraUserInfo
        updateUser(t, userName, info);
    }


    @Override
    public void removeUserRoles(Token t, String userName, List<UserPermissions> roles) throws AuthClientException {
        CarraUserInfo info = getUserInfo(t, userName);
        for (UserPermissions role : roles) {
            info.getAssertions().remove(role.toAssertionString());
        }
        updateUser(t, userName, info);
    }


    /**
     * This method handles the common exception handling
     */
    private void checkResponse(Status s) throws AuthClientException {
        if (s == null) {
            throw new AuthClientException("Status came back null, this shouldn't have happened, " +
                    "file a bug against restlet");
        }
        if (s.equals(Status.CLIENT_ERROR_UNAUTHORIZED)) {
            throw new PermissionException("Unauthorized");

        } else if (s.equals(Status.CLIENT_ERROR_BAD_REQUEST)) {
            throw new AuthClientException("Client sent malformed data");
        } else if (s.equals(Status.SERVER_ERROR_INTERNAL)) {
            throw new ServerException("Internal server errors");
        } else if (s.equals(Status.CLIENT_ERROR_NOT_FOUND)) {
            throw new ResourceNotFoundException("Unknown Resource");
        }

        return;
    }


    private ClientResource generateUserTokenResource(String userName) {
        userName = userName.trim();

        ClientResource returnVal = new ClientResource(
                endpointPrefix.toExternalForm() + "users/" + userName + "/authorization_token");
        return returnVal;

    }

    @Override
    public List<CarraUserInfo> getUsers(Token t) throws AuthClientException {
        ClientResource userAddJustResource = new ClientResource(
                endpointPrefix.toExternalForm() + "users/");

        try {
            Form f = new Form();
            f.add("x-carra-auth", t.getAuthString());
            userAddJustResource.getRequestAttributes().put("org.restlet.http.headers", f);

            Representation returnRep;
            try {
                returnRep = userAddJustResource.get();
            } catch (ResourceException e) {
                throw new AuthClientException("Unknown error");
            }

            CarraUsers users;
            checkResponse(userAddJustResource.getStatus());
            try {
                users = JAXBUtils.unmarshal(returnRep.getText(), CarraUsers.class);
            } catch (JAXBException e) {
                throw new ServerException("Bad response");
            } catch (IOException e) {
                throw new ServerException("Bad response");
            }

            return users.getUsers();
        } finally {
            userAddJustResource.release();
        }
    }

    @Override
    public AuthCapabilities getCapabilities(Token t) throws AuthClientException {
        ClientResource userAddJustResource = new ClientResource(
                endpointPrefix.toExternalForm() + "capabilities/");

        try {
            Form f = new Form();
            f.add("x-carra-auth", t.getAuthString());
            userAddJustResource.getRequestAttributes().put("org.restlet.http.headers", f);

            Representation returnRep;
            try {
                returnRep = userAddJustResource.get();
            } catch (ResourceException e) {
                throw new AuthClientException("Unknown error");
            }

            AuthCapabilities capabilities;
            checkResponse(userAddJustResource.getStatus());
            try {
                capabilities = JAXBUtils.unmarshal(returnRep.getText(), AuthCapabilities.class);
            } catch (JAXBException e) {
                throw new ServerException("Bad response");
            } catch (IOException e) {
                throw new ServerException("Bad response");
            }

            return capabilities;
        } finally {
            userAddJustResource.release();
        }
    }
}
