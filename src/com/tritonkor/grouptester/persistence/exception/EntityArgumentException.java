package com.tritonkor.grouptester.persistence.exception;

import java.util.List;

public class EntityArgumentException extends IllegalArgumentException {

    private List<String> errors;

    public EntityArgumentException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
