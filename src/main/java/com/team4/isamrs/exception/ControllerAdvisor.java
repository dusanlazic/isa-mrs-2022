package com.team4.isamrs.exception;

import com.team4.isamrs.exception.error.ExceptionResponseBody;
import com.team4.isamrs.exception.error.ValidationErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ex.getBindingResult().getGlobalErrors().forEach((error) -> {
            String objectName = ((ObjectError) error).getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(objectName, errorMessage);
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ExceptionResponseBody handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return new ExceptionResponseBody(
                HttpStatus.CONFLICT.value(),
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ExceptionResponseBody handlePhoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex) {
        return new ExceptionResponseBody(
                HttpStatus.CONFLICT.value(),
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(ConfirmationLinkExpiredException.class)
    public ExceptionResponseBody handleConfirmationLinkExpiredException(ConfirmationLinkExpiredException ex) {
        return new ExceptionResponseBody(
                HttpStatus.GONE.value(),
                ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RegistrationRequestAlreadyResolvedException.class)
    public ExceptionResponseBody handleRegistrationRequestAlreadyResolvedException(RegistrationRequestAlreadyResolvedException ex) {
        return new ExceptionResponseBody(
                HttpStatus.CONFLICT.value(),
                "Cannot respond to the registration request since it is already resolved.");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PasswordSameAsOldException.class)
    public ExceptionResponseBody handlePasswordSameAsOldException(PasswordSameAsOldException ex) {
        return new ExceptionResponseBody(
                HttpStatus.CONFLICT.value(),
                "New password cannot be the same as old.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(IncorrectCurrentPasswordException.class)
    public ExceptionResponseBody handleIncorrectCurrentPasswordException(IncorrectCurrentPasswordException ex) {
        return new ExceptionResponseBody(
                HttpStatus.FORBIDDEN.value(),
                "Incorrect current password.");
    }
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ReservationsInUnavailabilityPeriodException.class)
    public ExceptionResponseBody handleReservationsInUnavailabilityPeriodException(ReservationsInUnavailabilityPeriodException ex) {
        return new ExceptionResponseBody(
                HttpStatus.CONFLICT.value(),
                "There are " + ex.getMessage() + " reservations in that unavailability period.");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IdenticalAvailabilityDatesException.class)
    public ExceptionResponseBody handleIdenticalAvailabilityDatesException(IdenticalAvailabilityDatesException ex) {
        return new ExceptionResponseBody(
                HttpStatus.CONFLICT.value(),
                "Availability period dates should not be the same.");
    }
}
