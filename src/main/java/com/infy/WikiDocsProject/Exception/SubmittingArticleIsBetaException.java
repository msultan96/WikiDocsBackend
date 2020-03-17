package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsBetaException extends RuntimeException {

    public SubmittingArticleIsBetaException() {
        super("ArticleService.SUBMITTING_ARTICLE_BETA");
    }
}
