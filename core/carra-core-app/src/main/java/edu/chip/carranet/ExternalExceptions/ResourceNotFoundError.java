package edu.chip.carranet.ExternalExceptions;



import javax.ws.rs.core.Response;

/**
 * Created by IntelliJ IDEA.
 * DecoratedUser: justinquan
 * Date: Mar 28, 2010
 * Time: 11:42:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceNotFoundError extends ExternalError {

    public ResourceNotFoundError() {
        super("No Such Resource", Response.Status.NOT_FOUND);
    }

    public ResourceNotFoundError(String message){
        super(message, Response.Status.NOT_FOUND);
    }

}
