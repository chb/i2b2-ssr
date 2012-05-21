package edu.chip.carranet.overlay.authorization;

import edu.chip.carranet.auth.backend.BackendUtil;
import edu.chip.carranet.auth.backend.TokenUtil;
import edu.chip.carranet.auth.backend.UserPermissions;
import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.overlay.persistance.PersistedResource;
import org.restlet.data.ChallengeResponse;
import org.spin.tools.crypto.signature.Identity;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 23, 2010
 */
public class OwnerAuthorization {

    /**
     *
     * Try not to pass in null resources, we'll just deny it if we don't expect it.
     * @param operation
     * @param resource
     * @return
     */
    public static void validateAccess(Enum operation, Identity id, PersistedResource resource)
            throws AuthorizationFailedError {
        BackendUtil.verifyTokenAndAdmin(id);
    }

    private static boolean safeEquals(String one, String two) {
        if(one == null || two == null) {
            return one == two;
        } else {
            //No case sensitive usernames, too annoying
            return one.equalsIgnoreCase(two);
        }
    }
}
