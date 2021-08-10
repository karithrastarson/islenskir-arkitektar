package com.karithrastarson.registry.exception;

public class DuplicateException extends Throwable {
    public DuplicateException(String id) {
        super("Item with identifier " + id + " already present in db");
    }
}
