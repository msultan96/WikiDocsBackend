package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsApprovedException extends Exception {
    public RejectingArticleIsApprovedException() {
        super("ArticleService.REJECTING_ARTICLE_APPROVED");
    }
}
