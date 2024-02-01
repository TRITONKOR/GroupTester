package com.tritonkor.grouptester.domain.exception;

/**
 * The SignUpException class is a runtime exception indicating an error during the user sign-up
 * process. It is thrown when an issue occurs while attempting to sign up a user.
 */
public class SignUpException extends RuntimeException {

    /**
     * Constructs a new SignUpException with the specified error message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                {@link #getMessage()} method).
     */
    public SignUpException(String message) {
        super(message);
    }
}
