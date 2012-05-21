package edu.chip.carranet.auth.exception;

/**
 * <p>
 * Base Exception for the Auth Service
 * </p>
 */
public abstract class AuthException extends Exception {

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
