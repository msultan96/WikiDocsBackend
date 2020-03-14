package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsDiscardedException extends RuntimeException {
    public ApprovingArticleIsDiscardedException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.APPROVING_ARTICLE_DISCARDED"
    }
}
