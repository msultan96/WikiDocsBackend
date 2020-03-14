package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsDiscardedException extends RuntimeException {
    public RejectingArticleIsDiscardedException( String message ) {
    	// Initialize Exception class with below message
            super( message ); //"ArticleService.REJECTING_ARTICLE_DISCARDED"
        }
}
