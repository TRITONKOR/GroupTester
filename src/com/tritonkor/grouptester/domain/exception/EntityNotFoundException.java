package com.tritonkor.grouptester.domain.exception;

/**
 * The EntityNotFoundException class is a runtime exception indicating that an entity was not found.
 * It is thrown when an attempt to retrieve an entity results in no matching entity being found.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new EntityNotFoundException with the specified error message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
