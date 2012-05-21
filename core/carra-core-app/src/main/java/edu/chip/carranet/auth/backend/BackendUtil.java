package edu.chip.carranet.auth.backend;


import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.auth.exception.PermissionException;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.spin.tools.crypto.signature.Identity;

import java.util.Set;

/**
 * Utility methods for creating and manipulating objects
 * commonly used in the backend.
 * <p/>
 * Not usual a
 */
public class BackendUtil {

    public static CarraUserInfo createUserInfo(String userName, Set<String> roles) {
        CarraUserInfo returnUserInfo = new CarraUserInfo();


        returnUserInfo.setUsername(userName);

        if (roles != null) {
            for (String s : roles) {
                returnUserInfo.getAssertions().add(s);
            }
        }

        return returnUserInfo;
    }


    public static void verifyToken(Identity id) {
        if (!TokenUtil.verifyToken(id)) {
            throw new AuthorizationFailedError("Bad Token");
        }
    }

    public static void verifyTokenAndAdmin(Identity id)  {
        if (!TokenUtil.verifyToken(id)) {
            throw new AuthorizationFailedError("Bad Token");
        }

        for (String s : id.getAssertion()) {
            if (s.equalsIgnoreCase(UserPermissions.admin.toAssertionString())) {
                return;
            }
        }
        throw new AuthorizationFailedError("User is not an administrator");
    }


}
