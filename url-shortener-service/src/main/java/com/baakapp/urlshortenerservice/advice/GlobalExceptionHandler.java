package com.baakapp.urlshortenerservice.advice;

import com.baakapp.urlshortenerservice.model.exception.ArgumentValidationError;
import com.baakapp.urlshortenerservice.model.exception.ErrorDetail;
import jakarta.el.MethodNotFoundException;
import jakarta.ws.rs.NotFoundException;
import com.baakapp.urlshortenerservice.model.exception.ErrorMessage;
import com.baakapp.urlshortenerservice.model.exception.InvalidParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArgumentValidationError> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<InvalidParam> invalidParams = getInvalidParams(ex);

        ArgumentValidationError argumentValidationError =
                ArgumentValidationError
                .builder()
                .error("ARGUMENT_VALIDATION_ERROR")
                .title("Request parameter did not validate.")
                .invalidParams(invalidParams)
                .build();
        return ResponseEntity.badRequest().body(argumentValidationError);
    }

    @ExceptionHandler({MethodNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundErrors(NotFoundException ex) {
        ErrorDetail errorDetail = new ErrorDetail("NOT_FOUND", ex.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(errorDetail);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> handleGenericErrors() {
        ErrorDetail errorDetail = new ErrorDetail("SYSTEM_ERROR", "The server encountered an error and could not complete your request. Please try again later.");
        ErrorMessage errorMessage = new ErrorMessage(errorDetail);
        return ResponseEntity.internalServerError().body(errorMessage);
    }

    private List<InvalidParam> getInvalidParams(MethodArgumentNotValidException ex) {
        List<InvalidParam> invalidParams = new ArrayList<>();

        for(Field param : Objects.requireNonNull(ex.getTarget()).getClass().getDeclaredFields()) {
            List<String> allErrorsOfOneParam =
                    ex
                    .getBindingResult()
                    .getFieldErrors(param.getName())
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();

            InvalidParam invalidParam =
                    InvalidParam
                    .builder()
                    .name(param.getName())
                    .reasons(allErrorsOfOneParam)
                    .build();

            invalidParams.add(invalidParam);
        }
        return invalidParams;
    }
}
