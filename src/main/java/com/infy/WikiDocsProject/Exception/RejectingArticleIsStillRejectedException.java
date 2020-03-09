package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsStillRejectedException extends Exception {

    public RejectingArticleIsStillRejectedException() {
        super("ArticleService.REJECTING_ARTICLE_REJECTED");
    }
}
