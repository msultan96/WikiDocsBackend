package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsDiscardedException extends RuntimeException {

    public SubmittingArticleIsDiscardedException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.SUBMITTING_ARTICLE_DISCARDED"
    }
}
