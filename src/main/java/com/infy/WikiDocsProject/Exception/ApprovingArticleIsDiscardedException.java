package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsDiscardedException extends Exception {
    public ApprovingArticleIsDiscardedException() {
        super("ArticleService.APPROVING_ARTICLE_DISCARDED");
    }
}
