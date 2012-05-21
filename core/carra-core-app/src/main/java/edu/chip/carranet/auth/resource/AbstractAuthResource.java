package edu.chip.carranet.auth.resource;

import edu.chip.carranet.auth.backend.TokenUtil;
import org.restlet.data.Form;
import org.restlet.resource.ServerResource;
import org.spin.tools.crypto.signature.Identity;

/**
 * This class contains helper methods that other resources can use
 */

public class AbstractAuthResource extends ServerResource {

    protected Identity getIdentity() {
        Form f = (Form) getRequest().getAttributes().get("org.restlet.http.headers");
        String authString = f.getFirstValue("x-carra-auth");
        Identity id = TokenUtil.authStringToIdentity(authString);
        return id;
    }
}
