package com.infy.WikiDocsProject.Exception;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.INVALID_CHANNEL_ID"
    }

}
