package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsApprovedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public RejectingArticleIsApprovedException(String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.REJECTING_ARTICLE_APPROVED"
    }
}
