package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsDiscardedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public RejectingArticleIsDiscardedException() {
        super("ArticleService.REJECTING_ARTICLE_DISCARDED");
    }

    public RejectingArticleIsDiscardedException(String message ) {
    	// Initialize Exception class with below message
            super( message ); //"ArticleService.REJECTING_ARTICLE_DISCARDED"
        }
}
