package com.api.notes.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandlers {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> errorHandler404(EntityNotFoundException exception){
        Map<String,String> errorResponse = new HashMap<>();
        String errorMessage = exception.getMessage();
        errorResponse.put("message", errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> errorHandler400(MethodArgumentNotValidException exception){
        List<ErrorValidationDTO> errors = exception.getFieldErrors().stream().map(ErrorValidationDTO::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> errorHandlerNotReadable400(HttpMessageNotReadableException exception){
        Map<String, String> errorResponse = new HashMap<>();
        String errorMessage = exception.getMessage();
        int colonIndex = errorMessage.indexOf(':');
        String truncateMessage = errorMessage.substring(0, colonIndex);
        errorResponse.put("message",truncateMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * DTO to show field and message on bad requests - 400
     */
    private record ErrorValidationDTO(String field, String error){
        public ErrorValidationDTO(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
