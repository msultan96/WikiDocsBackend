package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsApprovedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ApprovingArticleIsApprovedException(){
        super("ArticleService.APPROVING_ARTICLE_APPROVED");
    }

    public ApprovingArticleIsApprovedException( String message) {
    	// Initialize Exception class with below message
        super( message ); //"ArticleService.APPROVING_ARTICLE_APPROVED"
    }
}
