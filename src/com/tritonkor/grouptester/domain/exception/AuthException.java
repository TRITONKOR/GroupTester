package com.tritonkor.grouptester.domain.exception;

/**
 * The AuthException class is a runtime exception indicating authentication failure. It is thrown
 * when there is an issue with the provided username or password during authentication.
 */
public class AuthException extends RuntimeException {

    /**
     * Constructs a new AuthException with the default error message. The default message is "Wrong
     * username or password."
     */
    public AuthException() {
        super("Wrong username or password");
    }
}
