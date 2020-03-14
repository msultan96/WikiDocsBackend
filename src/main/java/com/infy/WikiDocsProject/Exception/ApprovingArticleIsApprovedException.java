package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsApprovedException extends RuntimeException {

    public ApprovingArticleIsApprovedException( String message) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.APPROVING_ARTICLE_APPROVED"
    }
}
