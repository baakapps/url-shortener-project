package com.baakapp.urlshortenerservice.advice;

import jakarta.el.MethodNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("ARGUMENT_VALIDATION_ERROR");
        problemDetail.setDetail(
                ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Request parameter could not be validated")
        );
        return problemDetail;
    }

    @ExceptionHandler({MethodNotFoundException.class})
    public ProblemDetail handleNotFoundErrors(MethodNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("NOT_FOUND");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler({Exception.class})
    public ProblemDetail handleGenericErrors() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("SYSTEM_ERROR");
        problemDetail.setDetail("The server encountered an error and could not complete your request. Please try again later.");
        return problemDetail;
    }
}
