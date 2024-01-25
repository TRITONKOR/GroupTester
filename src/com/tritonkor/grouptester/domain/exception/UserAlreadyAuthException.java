package com.tritonkor.grouptester.domain.exception;

public class UserAlreadyAuthException extends RuntimeException {

    public UserAlreadyAuthException(String message) {
        super(message);
    }
}
