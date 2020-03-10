package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsStillRejectedException extends Exception {

    public RejectingArticleIsStillRejectedException() {
    	// Initialize Exception class with below message
        super("ArticleService.REJECTING_ARTICLE_REJECTED");
    }
}
