package edu.chip.carranet.auth.exception;


/**
 * This Exception will eventually cause the filter to throw a 404
 */
public class PermissionException extends AuthException {

    public PermissionException(String s) {
        super(s);
    }

}
