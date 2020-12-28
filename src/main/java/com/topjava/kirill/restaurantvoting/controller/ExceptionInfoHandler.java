package com.topjava.kirill.restaurantvoting.controller;

import com.topjava.kirill.restaurantvoting.model.User;
import com.topjava.kirill.restaurantvoting.util.ValidationUtil;
import com.topjava.kirill.restaurantvoting.util.exception.ErrorInfo;
import com.topjava.kirill.restaurantvoting.util.exception.ErrorType;
import com.topjava.kirill.restaurantvoting.util.exception.NotFoundException;
import com.topjava.kirill.restaurantvoting.util.exception.VoteDeadlineReachedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

import static com.topjava.kirill.restaurantvoting.util.exception.ErrorType.DATA_NOT_FOUND;
import static com.topjava.kirill.restaurantvoting.util.exception.ErrorType.VALIDATION_ERROR;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@Slf4j
public class ExceptionInfoHandler {

    public static final String NON_UNIQUE_EMAIL_MESSAGE = "User with this email already exists";

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(VoteDeadlineReachedException.class)
    public ErrorInfo handleError(HttpServletRequest req, VoteDeadlineReachedException e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        String rootCauseMessage = rootCause.getMessage();
        String message;
        if (rootCauseMessage.contains(User.CONSTRAINT_INDEX)) {
            message = NON_UNIQUE_EMAIL_MESSAGE;
        } else {
            message = rootCauseMessage;
        }
        return logAndGetErrorInfo(req, e, false, ErrorType.DATA_ERROR, message);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() :
                ((MethodArgumentNotValidException) e).getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, details);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType,
                details.length != 0 ? details : new String[]{rootCause.getLocalizedMessage()});
    }
}
