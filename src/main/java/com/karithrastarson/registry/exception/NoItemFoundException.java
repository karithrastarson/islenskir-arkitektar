package com.karithrastarson.registry.exception;

public class NoItemFoundException extends Exception {
    private String message;

    public NoItemFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
