package org.example.springbatch.exception.type;

public class AppInvalidException extends RuntimeException {

    public AppInvalidException(String message) {
        super(message);
    }
}
