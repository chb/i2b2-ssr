package edu.chip.carranet.overlay.shared;

import org.restlet.Request;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Parameter;
import org.restlet.engine.http.header.ChallengeWriter;
import org.restlet.engine.security.AuthenticatorHelper;
import org.restlet.util.Series;

/**
 * This class is a helper for the Restlet Engine to interpret custom
 * types of HTTP Authorizations.  The type that we use for this service is:
 * <p/>
 * Authorization: CARRA ABC123XXX
 * <p/>
 * where ABC123XXX is spin's base64 encoded digitally signed JAXB Identity
 *
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 26, 2010
 * @See TokenUtil for details on how to unravel the identity.
 */
public class CarranetAuthenticatorHelper extends AuthenticatorHelper {
    // The name of this scheme is so fantastically fragile.  It's what
    // Restlet's HTTP parser interpetted the challenge scheme to be, so it's saved here.
    // {@link
    //   org.restlet.engine.security.AuthenticatorUtils#parseAuthorizationHeader}
    public static final ChallengeScheme scheme =
            new ChallengeScheme("HTTP_CARRA", "CARRA", null);

    public CarranetAuthenticatorHelper() {
        this(scheme, true, false);
    }

    /**
     * Constructor.
     *
     * @param challengeScheme The supported challenge scheme.
     * @param clientSide      Indicates if client side authentication is supported.
     * @param serverSide      Indicates if server side authentication is supported.
     */
    private CarranetAuthenticatorHelper(ChallengeScheme challengeScheme, boolean clientSide, boolean serverSide) {
        super(challengeScheme, clientSide, serverSide);
    }

    @Override
    public void formatRawResponse(ChallengeWriter cw, ChallengeResponse challenge, Request request, Series<Parameter> httpHeaders) {
        cw.write(challenge.getRawValue());
    }


}

