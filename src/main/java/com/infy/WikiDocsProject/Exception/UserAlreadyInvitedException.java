package com.infy.WikiDocsProject.Exception;

public class UserAlreadyInvitedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public UserAlreadyInvitedException(String message) {
        super(message);
    }
}
