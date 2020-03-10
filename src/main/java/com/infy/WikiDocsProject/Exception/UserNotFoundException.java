package com.infy.WikiDocsProject.Exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
    	// Initialize Exception class with below message
        super("UserService.USER_NOT_FOUND");
    }
}
