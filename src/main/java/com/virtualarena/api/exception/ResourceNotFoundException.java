package com.virtualarena.api.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s not found with %s : %s", resource, field, value));
    }

    public ResourceNotFoundException(String resource, String field, Long value) {
        this(resource, field, String.valueOf(value));
    }
}
