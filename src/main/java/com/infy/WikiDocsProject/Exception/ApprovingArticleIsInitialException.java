package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsInitialException extends RuntimeException {

    public ApprovingArticleIsInitialException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.APPROVING_ARTICLE_INITIAL"
    }
}
