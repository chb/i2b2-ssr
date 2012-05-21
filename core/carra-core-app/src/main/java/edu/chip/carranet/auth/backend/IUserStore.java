package edu.chip.carranet.auth.backend;

import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.spin.tools.crypto.signature.Identity;

import java.util.List;


/**
 * <p>
 * This interface describes the basic interaction we need to have with the util user store
 * whether it's ldap or a something stored someplace stupid we need to be able to talk to it.
 * <p/>
 * There is no
 * </p>
 */
public interface IUserStore {

    /**
     * <p>
     * Returns a list of all the users currently in the edu.chip.carranet.auth system
     * </p>
     *
     * @param id id
     * @return
     */
    public List<CarraUserInfo> getUsers(Identity id) throws AuthorizationFailedError;

    /**
     * @param id
     * @param userName
     * @param info
     */
    public void updateUserInfo(Identity id, String userName, CarraUserInfo info);


    /**
     * Gets user information, can only be done by an admin
     *
     * @param id
     * @param userName
     * @return
     * @throws edu.chip.carranet.auth.exception.AuthException
     *
     */
    public CarraUserInfo getUserInfo(Identity id, String userName);

    /**
     * <p>
     * Adds a user to the system
     * </p>
     *
     * @param id       id
     * @param userName username
     * @param password possword
     */
    public void addUser(Identity id, String userName, String password);


    /**
     * Delete a user from the
     *
     * @param userName
     */
    public void deleteUser(Identity id, String userName);


    /**
     * Returns true if the user store contains information for this user
     *
     * @param id
     * @param userName
     * @return
     */
    public boolean containsUser(Identity id, String userName);


}
