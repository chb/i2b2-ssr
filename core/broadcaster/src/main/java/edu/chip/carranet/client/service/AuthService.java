package edu.chip.carranet.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.chip.carranet.client.data.UserDTO;

import java.util.ArrayList;


/**
 * GWT auth service
 */
@RemoteServiceRelativePath("authorization")
public interface AuthService extends RemoteService {

    public String authorizeUser(String userName, String password);

    public boolean isSessionActive(String sessionId);

    public ArrayList<UserDTO> getUsers();

    public UserDTO deleteUser(UserDTO u);

    public UserDTO addUser(UserDTO u, String password);

    public void logout();

    public UserDTO getUser(String userName);

    public UserDTO updateUser(UserDTO u);

    public boolean canManagePasswords();

}
