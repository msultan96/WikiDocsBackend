package com.infy.WikiDocsProject.Exception;

public class ArticleNotFoundException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ArticleNotFoundException() {
        super("ArticleService.INVALID_ID");
    }

    public ArticleNotFoundException(String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.INVALID_CHANNEL_ID"
    }

}
