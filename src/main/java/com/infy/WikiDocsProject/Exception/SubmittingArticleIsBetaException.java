package com.infy.WikiDocsProject.Exception;

public class SubmittingArticleIsBetaException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public SubmittingArticleIsBetaException(String message) {
        super(message);
    }
}
