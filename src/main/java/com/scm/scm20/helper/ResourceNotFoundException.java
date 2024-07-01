package com.scm.scm20.helper;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String massage) {
        super(massage);
    }

    public ResourceNotFoundException() {
        super("Resource Not Found");
    }

}
