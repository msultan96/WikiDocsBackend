package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsDiscardedException extends Exception {
    public ApprovingArticleIsDiscardedException() {
    	// Initialize Exception class with below message
        super("ArticleService.APPROVING_ARTICLE_DISCARDED");
    }
}
