package com.infy.WikiDocsProject.Exception;

public class UserNotFoundException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public UserNotFoundException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"UserService.USER_NOT_FOUND"
    }
}
