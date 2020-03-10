package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsDiscardedException extends Exception {

    public SubmittingArticleIsDiscardedException() {
    	// Initialize Exception class with below message
        super("ArticleService.SUBMITTING_ARTICLE_DISCARDED");
    }
}
