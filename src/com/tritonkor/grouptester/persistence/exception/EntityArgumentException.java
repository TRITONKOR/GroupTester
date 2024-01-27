package com.tritonkor.grouptester.persistence.exception;

import java.util.Set;

public class EntityArgumentException extends IllegalArgumentException {



    public EntityArgumentException(String errors) {
        super(errors);
    }

}
