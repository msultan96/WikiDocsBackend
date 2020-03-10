package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsApprovedException extends Exception {

    public ApprovingArticleIsApprovedException() {
    	// Initialize Exception class with below message
        super("ArticleService.APPROVING_ARTICLE_APPROVED");
    }
}
