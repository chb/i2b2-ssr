package edu.chip.carranet.auth.exception;


public class ServerException extends AuthException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String msg, Throwable t) {
        super(msg, t);
    }

}
