package edu.chip.carranet.auth;

import edu.chip.carranet.auth.backend.TokenUtil;
import org.spin.query.message.identity.IdentityService;
import org.spin.query.message.identity.IdentityServiceException;
import org.spin.tools.crypto.signature.Identity;


/**
 * This is an identity service implementation that takes a user name
 * and the Carranet AuthString.  The AuthString will be generated by the AuthService/PMFacade
 * <p/>
 * AuthString = base64(unmarshal(sign(identity)))
 */
public class CarraIdentityService implements IdentityService {


    /**
     * @param domain   doesn't matter
     * @param username the username
     * @param password the authString
     * @return
     * @throws IdentityServiceException
     */
    @Override
    public Identity certify(String domain, String username, String password)
            throws IdentityServiceException {

        if (username == null || password == null) {
            throw new IdentityServiceException("No username or password");
        }
        try {
            Identity id = TokenUtil.convertAuthToIdentity(password);
            if (username.equalsIgnoreCase(id.getUsername())) {
                return id;
            }
            throw new IdentityServiceException("Not authorized");

        } catch (Exception e) {
            throw new IdentityServiceException("Not authorized");
        }
    }
}
