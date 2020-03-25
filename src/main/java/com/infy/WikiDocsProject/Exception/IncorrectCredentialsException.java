package com.infy.WikiDocsProject.Exception;

public class IncorrectCredentialsException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public IncorrectCredentialsException(String message ) {
        super( message ); //"UserService.INCORRECT_PASSWORD"
    }
}
