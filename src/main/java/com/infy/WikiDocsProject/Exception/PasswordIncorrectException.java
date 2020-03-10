package com.infy.WikiDocsProject.Exception;

public class PasswordIncorrectException extends Exception {
    public PasswordIncorrectException() {
        super("UserService.INCORRECT_PASSWORD");
    }
}
