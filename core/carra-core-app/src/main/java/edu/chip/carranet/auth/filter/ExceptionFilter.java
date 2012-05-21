package edu.chip.carranet.auth.filter;


import edu.chip.carranet.auth.exception.ClientException;
import edu.chip.carranet.auth.exception.PermissionException;
import edu.chip.carranet.auth.exception.ResourceNotFoundException;
import edu.chip.carranet.auth.exception.ServerException;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;


/**
 * This filter catches exceptions thrown from the util and converts them
 * into HTTP error codes
 * <p/>
 * AuthClientException - Throws a 500
 * ClientException - throws 400
 * PermissionException - throws a 404
 */
public class ExceptionFilter extends Filter {


    @Override
    protected void afterHandle(Request request, Response response) {

        if (response.getStatus().getThrowable() instanceof PermissionException) {
            response.setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
            return;
        } else if (response.getStatus().getThrowable() instanceof ClientException) {
            response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return;
        } else if (response.getStatus().getThrowable() instanceof ServerException) {
            response.setStatus(Status.SERVER_ERROR_INTERNAL);
            return;
        } else if (response.getStatus().getThrowable() instanceof ResourceNotFoundException) {
            response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return;
        }


    }


}