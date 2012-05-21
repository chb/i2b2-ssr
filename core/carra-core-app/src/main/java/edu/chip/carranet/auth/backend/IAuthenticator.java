package edu.chip.carranet.auth.backend;

import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.auth.exception.ClientException;
import edu.chip.carranet.jaxb.AuthCapabilities;
import org.spin.tools.crypto.signature.Identity;


/**
 * This interface is just a way to say a class that takes a username and password and says yay or nay
 */
public interface IAuthenticator {

    /**
     * Authorize the user, throws permission exception when the user isn't found
     *
     * @param username
     * @param password
     * @return
     */
    public Identity authenticate(String username, String password)
            throws AuthorizationFailedError;

    /**
     * Find the user by username, throw ClientException if not found
     *
     * @param username
     * @return
     * @throws edu.chip.carranet.auth.exception.ClientException
     *
     */
    public User findUser(String username) throws ClientException;

    public void changeUserPassword(Identity id, String username, String oldPassword, String newPassword) throws AuthException;

    public AuthCapabilities getAuthCapabilities();

}
