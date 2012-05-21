package edu.chip.carranet.ExternalExceptions;

import org.restlet.data.Status;

import javax.ws.rs.core.Response;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 26, 2010
 */
public class AuthorizationFailedError extends ExternalError {

    public AuthorizationFailedError() {
        super("Authorization Failed", Response.Status.FORBIDDEN);
    }

    public AuthorizationFailedError(String message){
        super(message, Response.Status.FORBIDDEN);

    }
}
