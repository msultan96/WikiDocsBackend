package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsStillRejectedException extends Exception {

    public ApprovingArticleIsStillRejectedException() {
    	// Initialize Exception class with below message
        super("ArticleService.APPROVING_ARTICLE_REJECTED");
    }
}
