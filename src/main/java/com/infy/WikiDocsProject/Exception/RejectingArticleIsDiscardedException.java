package com.infy.WikiDocsProject.Exception;

public class RejectingArticleIsDiscardedException extends Exception {
    public RejectingArticleIsDiscardedException() {
        super("ArticleService.REJECTING_ARTICLE_DISCARDED");
    }
}
