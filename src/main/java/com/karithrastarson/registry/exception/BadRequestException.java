package com.karithrastarson.registry.exception;

public class BadRequestException extends Exception {

    public BadRequestException(String parameter) { super("Parameter \"" + parameter + "\" missing or malformed");
    }
}
