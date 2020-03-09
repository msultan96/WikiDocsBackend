package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsStillRejectedException extends Exception {

    public ApprovingArticleIsStillRejectedException() {
        super("ArticleService.APPROVING_ARTICLE_REJECTED");
    }
}
