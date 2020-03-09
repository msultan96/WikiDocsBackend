package com.infy.WikiDocsProject.Exception;

public class ArticleNotFoundException extends Exception {

    public ArticleNotFoundException() {
        super("ArticleService.INVALID_CHANNEL_ID");
    }

}
