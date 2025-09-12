package ro.scoalainformala.studentmgmt.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException notFoundException) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(notFoundException.getMessage())
                .details("The request was not found.")
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(final ValidationException validationException) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(validationException.getMessage())
                .details("Validation failed for the provided input.")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
