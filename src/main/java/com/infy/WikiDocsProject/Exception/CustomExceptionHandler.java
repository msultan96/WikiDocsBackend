package com.infy.WikiDocsProject.Exception;

import com.infy.WikiDocsProject.Model.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.springframework.http.HttpStatus.*;

/**
 * CustomExceptionHandler to handle all RuntimeExceptions
 * @author: Daniel Neal
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @Autowired
    private final Environment environment;

    static Logger log = LogManager.getLogger(CustomExceptionHandler.class);

    /**
     * Constructor
     * @param environment
     */
    public CustomExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    /**
     * ExceptionHandler for the ApprovingArticleIsApprovedException
     */
    @ExceptionHandler({ApprovingArticleIsApprovedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsApprovedException(ApprovingArticleIsApprovedException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the ApprovingArticleIsDiscardedException
     */
    @ExceptionHandler({ApprovingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsDiscardedException(ApprovingArticleIsDiscardedException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the ApprovingArticleIsInitialException
     */
    @ExceptionHandler({ApprovingArticleIsInitialException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsInitialException(ApprovingArticleIsInitialException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the ApprovingArticleIsStillRejectedException
     */
    @ExceptionHandler({ApprovingArticleIsStillRejectedException.class})
    public ResponseEntity<ErrorMessage> approvingArticleIsStillRejectedException(ApprovingArticleIsStillRejectedException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the RejectingArticleIsApprovedException
     */
    @ExceptionHandler({RejectingArticleIsApprovedException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsApprovedException(RejectingArticleIsApprovedException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the RejectingArticleIsDiscardedException
     */
    @ExceptionHandler({RejectingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsDiscardedException(RejectingArticleIsDiscardedException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the RejectingArticleIsInitialException
     */
    @ExceptionHandler({RejectingArticleIsInitialException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsInitialException(RejectingArticleIsInitialException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the RejectingArticleIsStillRejectedException
     */
    @ExceptionHandler({RejectingArticleIsStillRejectedException.class})
    public ResponseEntity<ErrorMessage> rejectingArticleIsStillRejectedException(RejectingArticleIsStillRejectedException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the SubmittingArticleIsApprovedException
     */
    @ExceptionHandler({SubmittingArticleIsApprovedException.class})
    public ResponseEntity<ErrorMessage> submittingArticleIsApprovedException(SubmittingArticleIsApprovedException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the SubmittingArticleIsBetaException
     */
    @ExceptionHandler({SubmittingArticleIsBetaException.class})
    public ResponseEntity<ErrorMessage> submittingArticleIsBetaException(SubmittingArticleIsBetaException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the SubmittingArticleIsDiscardedException
     */
    @ExceptionHandler({SubmittingArticleIsDiscardedException.class})
    public ResponseEntity<ErrorMessage> submittingArticleIsDiscardedException(SubmittingArticleIsDiscardedException e) {
        return error(BAD_REQUEST, e);
    }

    /**
     * ExceptionHandler for the ArticleNotFoundException
     */
    @ExceptionHandler({ArticleNotFoundException.class})
    public ResponseEntity<ErrorMessage> articleNotFoundException(ArticleNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    /**
     * ExceptionHandler for the UserNotFoundException
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException e) {
        return error(NOT_FOUND, e);
    }

    /**
     * ExceptionHandler for the UserAlreadyExistsException
     */
    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(UserAlreadyExistsException e){
        return error(CONFLICT, e);
    }

    /**
     * ExceptionHandler for the IncorrectCredentialsException
     */
    @ExceptionHandler({IncorrectCredentialsException.class})
    public ResponseEntity<ErrorMessage> incorrectCredentialsException(IncorrectCredentialsException e) {
        return error(UNAUTHORIZED, e);
    }

    /**
     * ExceptionHandler for the {@link UserAlreadyInvitedException}
     */
    @ExceptionHandler({UserAlreadyInvitedException.class})
    public ResponseEntity<ErrorMessage> userAlreadyInvitedException(UserAlreadyInvitedException e) {
        return error(CONFLICT, e);
    }

    /**
     * ExceptionHandler for the {@link UsernameNotFoundException}
     */
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorMessage> UsernameNotFoundException(UsernameNotFoundException e) {
        return error(UNAUTHORIZED, e);
    }

    /**
     * ExceptionHandler for all other {@link RuntimeException}
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorMessage> exception(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    /**
     * error, logs any exceptions that have ocurred
     * @param status
     * @param e
     * @return new ResponseEntity<>(error,status)
     */
    private ResponseEntity<ErrorMessage> error(HttpStatus status, Exception e) {
        logger.error("The following exception occurred during processing:  ", e);
        logger.info(environment.getProperty(e.getMessage()));
        ErrorMessage error = new ErrorMessage(status.value(), environment.getProperty(e.getMessage()));
        return new ResponseEntity<>(error, status);
    }
}