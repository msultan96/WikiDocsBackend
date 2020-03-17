package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsDiscardedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ApprovingArticleIsDiscardedException() {
        super("ArticleService.APPROVING_ARTICLE_DISCARDED");
    }

    public ApprovingArticleIsDiscardedException(String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.APPROVING_ARTICLE_DISCARDED"
    }
}
