package com.tritonkor.grouptester.persistence.exception;

/**
 * {@code EntityArgumentException} is an exception that indicates invalid arguments or data for an
 * entity, often related to validation errors.
 */
public class EntityArgumentException extends IllegalArgumentException {

    /**
     * Constructs a new {@code EntityArgumentException} with the specified error message.
     *
     * @param errors The error message describing the entity argument exception.
     */
    public EntityArgumentException(String errors) {
        super(errors);
    }

}
