package com.infy.WikiDocsProject.Exception;

public class ApprovingArticleIsApprovedException extends Exception {

    public ApprovingArticleIsApprovedException() {
        super("ArticleService.APPROVING_ARTICLE_APPROVED");
    }
}
