package com.infy.WikiDocsProject.Exception;

import com.infy.WikiDocsProject.Model.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private final Environment environment;

    public CustomExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    @ExceptionHandler({ApprovingArticleIsApprovedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsApprovedException(ApprovingArticleIsApprovedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({ApprovingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsDiscardedException(ApprovingArticleIsDiscardedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({ApprovingArticleIsInitialException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsInitialException(ApprovingArticleIsInitialException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({ApprovingArticleIsStillRejectedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsStillRejectedException(ApprovingArticleIsStillRejectedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({RejectingArticleIsApprovedException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsApprovedException(RejectingArticleIsApprovedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({RejectingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsDiscardedException(RejectingArticleIsDiscardedException e) {
        return error(BAD_REQUEST, e);
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
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({SubmittingArticleIsBetaException.class})
    public ResponseEntity<ErrorMessage> submittingArticleIsBetaException(SubmittingArticleIsBetaException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({SubmittingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> submittingArticleIsDiscardedException(SubmittingArticleIsDiscardedException e) {
        return error(BAD_REQUEST, e);
    }

    @ExceptionHandler({ArticleNotFoundException.class})
    public ResponseEntity<ErrorMessage> articleNotFoundException(ArticleNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(UserAlreadyExistsException e){
        return error(CONFLICT, e);
    }

    @ExceptionHandler({PasswordIncorrectException.class})
    public ResponseEntity<ErrorMessage> passwordIncorrectException(PasswordIncorrectException e) {
        return error(UNAUTHORIZED, e);
    }

    @ExceptionHandler({UserAlreadyInvitedException.class})
    public ResponseEntity<ErrorMessage> userAlreadyInvitedException(UserAlreadyInvitedException e) {
        return error(CONFLICT, e);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorMessage> exception(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    private ResponseEntity<ErrorMessage> error(HttpStatus status, Exception e) {
        log.error("The following exception occurred during processing:  ", e);
        log.info(environment.getProperty(e.getMessage()));
        ErrorMessage error = new ErrorMessage(status.value(), environment.getProperty(e.getMessage()));
        return new ResponseEntity<>(error, status);
    }
}