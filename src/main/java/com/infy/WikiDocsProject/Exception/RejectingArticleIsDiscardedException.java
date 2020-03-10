package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsDiscardedException extends Exception {
    public RejectingArticleIsDiscardedException() {
    	// Initialize Exception class with below message
        super("ArticleService.REJECTING_ARTICLE_DISCARDED");
    }
}
