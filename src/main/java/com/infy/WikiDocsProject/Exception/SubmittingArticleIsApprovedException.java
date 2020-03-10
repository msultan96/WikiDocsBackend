package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsApprovedException extends Exception {
    public SubmittingArticleIsApprovedException() {
    	// Initialize Exception class with below message
        super("ArticleService.SUBMITTING_ARTICLE_APPROVED");
    }
}
