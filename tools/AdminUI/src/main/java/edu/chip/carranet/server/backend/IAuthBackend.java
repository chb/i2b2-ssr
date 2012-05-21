package edu.chip.carranet.server.backend;

import edu.carranet.client.auth.Token;
import edu.carranet.client.exception.AuthClientException;
import edu.chip.carranet.client.data.UserDTO;

import java.util.ArrayList;

/**
 * Defines the necessary methods that a UI Auth backend must implement
 * will be adding more
 */
public interface IAuthBackend {

    public Token authorizeUser(String userName, String password)
            throws AuthClientException;

    public ArrayList<UserDTO> getUsers(Token t)
            throws AuthClientException;

    public UserDTO deleteUser(Token t, UserDTO u)
            throws AuthClientException;

    public UserDTO addUser(Token t, UserDTO u, String newPassword)
            throws AuthClientException;

    public UserDTO updateUser(Token t, UserDTO dto)
            throws AuthClientException;

    public UserDTO getUser(Token t, String userName)
            throws AuthClientException;

    public boolean canChangePassword(Token t) throws AuthClientException;


}
