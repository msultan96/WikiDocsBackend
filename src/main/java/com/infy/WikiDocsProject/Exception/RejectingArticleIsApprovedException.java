package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsApprovedException extends Exception {
    public RejectingArticleIsApprovedException() {
    	// Initialize Exception class with below message
        super("ArticleService.REJECTING_ARTICLE_APPROVED");
    }
}
