package com.tritonkor.grouptester.persistence.exception;

public class EntityArgumentException extends IllegalArgumentException {

    public EntityArgumentException(String errors) {
        super(errors);
    }

}
