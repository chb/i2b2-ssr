package edu.chip.carranet.ExternalExceptions;

import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Extend this type of exception if you are about to throw something
 * that's meant to be caught and translated into something useful
 * meaning for the client of the service.
 *
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 26, 2010
 */
public class ExternalError extends WebApplicationException {

    private static final Logger log = Logger.getLogger(ExternalError.class);

    private String msg;
    private Response.Status status;

    public ExternalError(String msg, Response.Status status) {
        super(status);
        log.fatal("External Error: " + msg + "StatusCode: " + status);
        this.msg = msg;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public Response.Status getStatus() {
        return status;
    }
}
