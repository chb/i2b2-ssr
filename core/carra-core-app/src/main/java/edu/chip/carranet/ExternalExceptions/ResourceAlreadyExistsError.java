package edu.chip.carranet.ExternalExceptions;

import javax.ws.rs.core.Response;

/**
 * Created by IntelliJ IDEA.
 * DecoratedUser: justinquan
 * Date: Mar 29, 2010
 * Time: 1:08:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceAlreadyExistsError extends ExternalError {

    public ResourceAlreadyExistsError(String s){
        super("Resource Already Exists", Response.Status.FORBIDDEN);
    }

    public ResourceAlreadyExistsError() {
        // TODO: reevaluate response
        super("Resource Already Exists", Response.Status.FORBIDDEN);
    }
}
