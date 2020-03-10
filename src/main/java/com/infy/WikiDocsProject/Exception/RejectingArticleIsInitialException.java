package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsInitialException extends Exception {
    public RejectingArticleIsInitialException() {
    	// Initialize Exception class with below message
        super("ArticleService.REJECTING_ARTICLE_INITIAL");
    }
}
