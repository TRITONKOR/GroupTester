package com.tritonkor.grouptester.persistence.exception;

import java.util.Set;

public class EntityArgumentException extends IllegalArgumentException {

    private Set<String> errors;

    public EntityArgumentException(Set<String> errors) {
        this.errors = errors;
    }

    public Set<String> getErrors() {
        return errors;
    }
}
