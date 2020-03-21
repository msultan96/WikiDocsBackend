package com.infy.WikiDocsProject.Exception;

public class PasswordIncorrectException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PasswordIncorrectException(String message ) {
        super( message ); //"UserService.INCORRECT_PASSWORD"
    }
}
