package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsApprovedException extends Exception {
    public SubmittingArticleIsApprovedException() {
        super("ArticleService.SUBMITTING_ARTICLE_APPROVED");
    }
}
