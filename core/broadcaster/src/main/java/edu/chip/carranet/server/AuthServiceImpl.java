package edu.chip.carranet.server;

import edu.carranet.client.auth.Token;
import edu.carranet.client.exception.AuthClientException;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.client.service.AuthService;
import edu.chip.carranet.server.backend.IAuthBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


/**
 * This class is just a stub for testing purposes,
 * it's going to go away one day (I hope)
 */
@Component
public class AuthServiceImpl extends CarraGwtService implements AuthService {


    @Autowired
    private IAuthBackend backend;


    public AuthServiceImpl() {

    }

    @Override
    public UserDTO getUser(String userName) {
        Token t = getAuthToken();
        if (t != null) {
            try {
                return backend.getUser(t, userName);
            } catch (Exception e) {
                //eat it and do nothing
            }

        }

        throw new RuntimeException("Permission Denied");
    }

    @Override
    public String authorizeUser(String userName, String password) {

        try {
            Token token = backend.authorizeUser(userName, password);
            setAuthToken(token);
            return this.getThreadLocalRequest().getSession().getId();
        } catch (AuthClientException e) {
            return null;
        }


    }

    @Override
    public ArrayList<UserDTO> getUsers() {
        Token t = getAuthToken();
        if (t != null) {
            try {
                return backend.getUsers(t);
            } catch (Exception e) {
                //eat it and do nothing
            }

        }

        throw new RuntimeException("Permission Denied");
    }

    @Override
    public UserDTO deleteUser(UserDTO u) {
        Token t = getAuthToken();
        if (t != null) {

            try {
                return backend.deleteUser(t, u);
            } catch (AuthClientException e) {
                // do nothing
            }
        }
        throw new RuntimeException("Permission Denied");
    }


    @Override
    public UserDTO addUser(UserDTO u, String password) {
        Token t = getAuthToken();
        if (t != null) {

            try {
                return backend.addUser(t, u, password);
            } catch (AuthClientException e) {
                //do nothing, let the other exception get thrown
            }
        }


        throw new RuntimeException("Permission Denied");
    }


    @Override
    public void logout() {
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession();
        session.invalidate();
    }

    public boolean isSessionActive(String sessionId) {
        if (sessionId == null) {
            return false;
        }

        String currentSessionId = this.getThreadLocalRequest().getSession().getId();
        return sessionId.equals(currentSessionId);

    }

    @Override
    public UserDTO updateUser(UserDTO u) {
        try {
            Token t = getAuthToken();
            return backend.updateUser(t, u);
        } catch (AuthClientException e) {
            return null;
        }
    }

    @Override
    public boolean canManagePasswords() {
        try {
            Token t = getAuthToken();
            return backend.canChangePassword(t);
        } catch (AuthClientException e) {
            return false;
        }
    }

}
