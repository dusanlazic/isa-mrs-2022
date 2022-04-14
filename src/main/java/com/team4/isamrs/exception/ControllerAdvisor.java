package com.team4.isamrs.exception;

import com.team4.isamrs.exception.error.ExceptionResponseBody;
import com.team4.isamrs.exception.error.ValidationErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponseBody handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ValidationErrorResponseBody(
                HttpStatus.BAD_REQUEST.value(),
                "Field validation failed.",
                errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ExceptionResponseBody handleNoSuchElementException(NoSuchElementException ex) {
        return new ExceptionResponseBody(
                HttpStatus.NOT_FOUND.value(),
                "Requested resource does not exist.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PhotoNotFoundException.class)
    public ExceptionResponseBody handlePhotoNotFoundException(PhotoNotFoundException ex) {
        return new ExceptionResponseBody(
                HttpStatus.NOT_FOUND.value(),
                "Requested photo does not exist.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PhotoStorageException.class)
    public ExceptionResponseBody handlePhotoStorageException(PhotoStorageException ex) {
        return new ExceptionResponseBody(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PhotoPathTraversalException.class)
    public ExceptionResponseBody handlePhotoPathTraversalException(PhotoPathTraversalException ex) {
        return new ExceptionResponseBody(
                HttpStatus.FORBIDDEN.value(),
                "Cannot read or write outside intended uploads directory. btw, how?");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PhotoUploadException.class)
    public ExceptionResponseBody handlePhotoUploadException(PhotoUploadException ex) {
        return new ExceptionResponseBody(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
    }
}