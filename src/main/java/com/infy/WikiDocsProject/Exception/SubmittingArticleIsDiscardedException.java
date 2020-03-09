package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsDiscardedException extends Exception {

    public SubmittingArticleIsDiscardedException() {
        super("ArticleService.SUBMITTING_ARTICLE_DISCARDED");
    }
}
