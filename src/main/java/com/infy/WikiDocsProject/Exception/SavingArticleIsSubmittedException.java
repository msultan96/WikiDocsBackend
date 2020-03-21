package com.infy.WikiDocsProject.Exception;

public class SavingArticleIsSubmittedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public SavingArticleIsSubmittedException(String message) {
        super(message);
    }
}
