package com.infy.WikiDocsProject.Exception;

public class ArticleNotFoundException extends Exception {

    public ArticleNotFoundException() {
    	// Initialize Exception class with below message
        super("ArticleService.INVALID_CHANNEL_ID");
    }

}
