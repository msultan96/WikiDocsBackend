package com.infy.WikiDocsProject.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public UserAlreadyExistsException() {
        super("UserService.EMAIL_IN_USE");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
