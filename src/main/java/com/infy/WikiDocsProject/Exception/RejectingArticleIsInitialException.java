package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsInitialException extends RuntimeException {
    public RejectingArticleIsInitialException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.REJECTING_ARTICLE_INITIAL"
    }
}
