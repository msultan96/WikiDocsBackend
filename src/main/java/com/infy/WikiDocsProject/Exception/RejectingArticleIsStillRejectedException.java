package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsStillRejectedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public RejectingArticleIsStillRejectedException(String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.REJECTING_ARTICLE_REJECTED"
    }
}
