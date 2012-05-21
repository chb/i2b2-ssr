package edu.carranet.client.auth;

import edu.carranet.client.exception.AuthClientException;
import edu.chip.carranet.auth.backend.UserPermissions;
import edu.chip.carranet.jaxb.AuthCapabilities;
import edu.chip.carranet.jaxb.CarraUserInfo;

import java.util.List;


public interface IAuthClient {


    /**
     * Adds a user to the system, may only be performed by an administrator
     *
     * @param t
     * @param userName
     * @param password
     */
    public void addUser(Token t, String userName, String password)
            throws AuthClientException;

    /**
     * Deletes a user from the system, may only be performed by an administrator
     *
     * @param t
     * @param userName
     */
    public void deleteUser(Token t, String userName)
            throws AuthClientException;

    /**
     * Changes a user password, a user may change his/her own password, if you attempt
     * to change the password of another user you must be an administrator
     *
     * @param t
     * @param username
     * @param newPassword
     */
    public void changeUserPassword(Token t, String username, String oldPassword, String newPassword)
            throws AuthClientException;

    /**
     * Gets the Token if you're properly authenticated, otherwise it'll throw
     * an exception
     *
     * @param password
     * @return
     */
    public Token getAuthorization(String username, String password)
            throws AuthClientException;


    /**
     * Gets a listing of studies the user is involved with, only an admin can do this,
     * if you wish to know about your own studies see @link{edu.carranet.edu.chip.carranet.auth.Token.getUserStudies}
     *
     * @param t
     * @param userName
     * @return
     */
    public List<String> getUserStudies(Token t, String userName)
            throws AuthClientException;

    /**
     * Adds a user to studies, only an admin can do this
     *
     * @param t
     * @param userName
     * @param studyNames
     */
    public void addUserToStudies(Token t, String userName, List<String> studyNames)
            throws AuthClientException;

    /**
     * Remove a user from listed studies, if you remove a user from a study
     * they weren't a part of, there will be no error and processing
     * will silently continue.  Only an admin may do this
     *
     * @param t
     * @param userName
     * @param studyNames
     */
    public void removeUserFromStudies(Token t, String userName, List<String> studyNames)
            throws AuthClientException;

    /**
     * Returns the roles of a user, may only be performed by an admin
     * if you want to get the roles
     *
     * @param t
     * @param userName
     * @return
     */
    public List<UserPermissions> getUserRoles(Token t, String userName)
            throws AuthClientException;

    /**
     * @param t
     * @param userName
     * @param roles
     */
    public void addRolesToUser(Token t, String userName, List<UserPermissions> roles)
            throws AuthClientException;


    /**
     * @param t
     * @param userName
     * @param roles
     * @throws AuthClientException
     */
    public void removeUserRoles(Token t, String userName, List<UserPermissions> roles) throws AuthClientException;

    /**
     * gets a list of users on the system
     *
     * @param t
     * @return
     * @throws AuthClientException
     */
    public List<CarraUserInfo> getUsers(Token t) throws
            AuthClientException;


    public void updateUser(Token t, String userName, CarraUserInfo info) throws AuthClientException;

    /**
     * @param t
     * @param userName
     * @return
     * @throws AuthClientException
     */
    public CarraUserInfo getUserInfo(Token t, String userName) throws AuthClientException;

    /**
     * returns the capabilities of the backend
     *
     * @param t
     * @return
     * @throws AuthClientException
     */
    public AuthCapabilities getCapabilities(Token t) throws AuthClientException;


}
