package ru.effective_mobile.social_media_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(PublishByAnotherUserException.class)
    protected ResponseEntity<Object> handlePublishByAnotherUserException(PublishByAnotherUserException e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    protected ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(RelationshipAlreadyExistException.class)
    protected ResponseEntity<Object> handleRelationshipAlreadyExistException(RelationshipAlreadyExistException e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Set<String> errorsSet = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorsSet.add(errorMessage);
        });
        return buildErrorArrayResponse(errorsSet.toArray(new String[0]));
    }

    private static ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse response = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message);
        return ResponseEntity.status(httpStatus.value()).body(response);
    }

    private static ResponseEntity<Object> buildErrorArrayResponse(String[] message) {
        ErrorArrayResponse response = new ErrorArrayResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    static class ErrorResponse {
        private final int status;
        private final String error;
        private final String message;

        public ErrorResponse(int status, String error, String message) {
            this.status = status;
            this.error = error;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }

    static class ErrorArrayResponse {
        private final int status;
        private final String error;
        private final String[] message;

        public ErrorArrayResponse(int status, String error, String[] message) {
            this.status = status;
            this.error = error;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String[] getMessage() {
            return message;
        }
    }
}
