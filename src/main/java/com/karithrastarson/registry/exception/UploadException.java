package com.karithrastarson.registry.exception;

public class UploadException extends Exception {

    public UploadException(String name) {
        super("Error uploading file with name " + name + ".");
    }
}
