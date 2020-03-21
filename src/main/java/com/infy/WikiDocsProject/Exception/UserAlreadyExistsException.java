package com.infy.WikiDocsProject.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
