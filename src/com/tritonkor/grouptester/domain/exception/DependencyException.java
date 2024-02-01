package com.tritonkor.grouptester.domain.exception;

/**
 * The DependencyException class is a runtime exception indicating an issue with dependencies. It is
 * thrown when there is a problem related to dependencies in the application.
 */
public class DependencyException extends RuntimeException {

    /**
     * Constructs a new DependencyException with the specified error message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                {@link #getMessage()} method).
     */
    public DependencyException(String message) {
        super(message);
    }
}
