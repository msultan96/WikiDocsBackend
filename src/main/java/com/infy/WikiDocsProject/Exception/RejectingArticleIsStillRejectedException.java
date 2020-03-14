package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsStillRejectedException extends RuntimeException {

    public RejectingArticleIsStillRejectedException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.REJECTING_ARTICLE_REJECTED"
    }
}
