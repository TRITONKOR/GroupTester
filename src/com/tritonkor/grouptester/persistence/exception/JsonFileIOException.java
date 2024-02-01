package com.tritonkor.grouptester.persistence.exception;

/**
 * {@code JsonFileIOException} is a runtime exception indicating an error related to JSON file I/O
 * operations.
 */
public class JsonFileIOException extends RuntimeException {

    /**
     * Constructs a new {@code JsonFileIOException} with the specified error message.
     *
     * @param message The error message describing the JSON file I/O exception.
     */
    public JsonFileIOException(String message) {
        super(message);
    }
}
