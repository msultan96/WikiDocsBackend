package com.infy.WikiDocsProject.Exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.infy.WikiDocsProject.Model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ApprovingArticleIsApprovedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsApprovedException(ApprovingArticleIsApprovedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({ApprovingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsDiscardedException(ApprovingArticleIsDiscardedException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({ApprovingArticleIsInitialException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsInitialException(ApprovingArticleIsInitialException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({ApprovingArticleIsStillRejectedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsStillRejectedException(ApprovingArticleIsStillRejectedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({ArticleNotFoundException.class})
    public ResponseEntity<ErrorMessage> articleNotFoundException(ArticleNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({PasswordIncorrectException.class})
    public ResponseEntity<ErrorMessage> passwordIncorrectException(PasswordIncorrectException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({RejectingArticleIsApprovedException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsApprovedException(RejectingArticleIsApprovedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({RejectingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsDiscardedException(RejectingArticleIsDiscardedException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({RejectingArticleIsInitialException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsInitialException(RejectingArticleIsInitialException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({RejectingArticleIsStillRejectedException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsStillRejectedException(RejectingArticleIsStillRejectedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({SubmittingArticleIsApprovedException.class})
    public ResponseEntity<ErrorMessage> submittingArticleIsApprovedException(SubmittingArticleIsApprovedException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({SubmittingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> submittingArticleIsDiscardedException(SubmittingArticleIsDiscardedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorMessage> exception(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    private ResponseEntity<ErrorMessage> error(HttpStatus status, Exception e) {
        log.error("Exception occurred during processing=", e);
        ErrorMessage error = new ErrorMessage(status.value(), e.getMessage());
        return new ResponseEntity<>(error, status);
    }
}