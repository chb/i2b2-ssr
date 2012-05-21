package edu.chip.carranet.auth.backend;

import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.spin.tools.crypto.signature.Identity;

import java.util.List;

/**
 * This class holds functionality that is used accross all UserStores
 * such as verifying the id token
 */
public abstract class AuthenticatedActionPerformer {

    protected BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();


    protected void verifyToken(Identity id) throws AuthorizationFailedError {
        if(!TokenUtil.verifyToken(id)) {
            throw new AuthorizationFailedError();
        }
    }

    protected void verifyTokenAndAdmin(Identity id) throws AuthorizationFailedError{
        if(!TokenUtil.verifyToken(id)) {
            throw new AuthorizationFailedError("Bad Token");
        }

        List<UserPermissions> perms = UserUtil.getRolesFromIdentity(id);

        if(!perms.contains(UserPermissions.admin)) {
            new AuthorizationFailedError("User is not an administrator");
        }

    }

    protected String hashPassword(String password, int salt) {
        return passwordEncryptor.encryptPassword(password);
    }

}
