package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsApprovedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public SubmittingArticleIsApprovedException() {
        super("ArticleService.SUBMITTING_ARTICLE_APPROVED");
    }

    public SubmittingArticleIsApprovedException(String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.SUBMITTING_ARTICLE_APPROVED"
    }
}
