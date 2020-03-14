package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsStillRejectedException extends RuntimeException {

    public ApprovingArticleIsStillRejectedException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.APPROVING_ARTICLE_REJECTED"
    }
}
