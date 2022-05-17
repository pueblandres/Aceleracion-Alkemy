package com.alkemy.ong.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String param) {
        super ("Error, " + param + " is not found.");
    }
}
