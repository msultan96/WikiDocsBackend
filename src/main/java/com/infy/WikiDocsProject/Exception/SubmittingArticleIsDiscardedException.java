package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsDiscardedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public SubmittingArticleIsDiscardedException() {
        super("ArticleService.SUBMITTING_ARTICLE_DISCARDED");
    }

    public SubmittingArticleIsDiscardedException(String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.SUBMITTING_ARTICLE_DISCARDED"
    }
}
