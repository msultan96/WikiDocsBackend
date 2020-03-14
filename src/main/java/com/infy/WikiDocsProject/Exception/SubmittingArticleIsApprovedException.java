package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsApprovedException extends RuntimeException {
    public SubmittingArticleIsApprovedException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.SUBMITTING_ARTICLE_APPROVED"
    }
}
