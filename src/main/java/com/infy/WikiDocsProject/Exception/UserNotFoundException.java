package com.infy.WikiDocsProject.Exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("UserService.USER_NOT_FOUND");
    }
}
