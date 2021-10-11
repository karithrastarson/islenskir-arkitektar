package com.karithrastarson.registry.exception;

public class NoItemFoundException extends Exception {

    public NoItemFoundException(String itemId) {
        super("Item with ID " + itemId + " not found");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
