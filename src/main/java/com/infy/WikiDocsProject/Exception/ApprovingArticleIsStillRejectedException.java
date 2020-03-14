package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsStillRejectedException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public ApprovingArticleIsStillRejectedException( String message ) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.APPROVING_ARTICLE_REJECTED"
    }
}
