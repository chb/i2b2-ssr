package edu.chip.carranet.ExternalExceptions;


import javax.ws.rs.core.Response;

/**
 * @author Justin Quan
 * @link http://chip.org Date: 7/19/11
 */
public class BadResourceError extends ExternalError {

    public BadResourceError(String msg) {
        super(msg, Response.Status.BAD_REQUEST);
    }
}
