package com.tritonkor.grouptester.domain.exception;

/**
 * The UserAlreadyAuthException class is a runtime exception indicating that a user is already
 * authenticated. It is thrown when an attempt is made to authenticate a user who is already logged
 * in.
 */
public class UserAlreadyAuthException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyAuthException with the specified error message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                {@link #getMessage()} method).
     */
    public UserAlreadyAuthException(String message) {
        super(message);
    }
}
