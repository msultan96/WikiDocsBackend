package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsInitialException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ApprovingArticleIsInitialException() {
        super("ArticleService.APPROVING_ARTICLE_INITIAL");
    }

    public ApprovingArticleIsInitialException(String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.APPROVING_ARTICLE_INITIAL"
    }
}
