package com.infy.WikiDocsProject.Exception;

public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException( String message ) {
        super( message ); //"UserService.INCORRECT_PASSWORD"
    }
}
