package edu.carranet.client.exception;

/**
 * This class gets thrown when the server throws a 404 (Permission denied)
 */
public class PermissionException extends AuthClientException {


    public PermissionException(String s) {
        super(s);
    }

}

