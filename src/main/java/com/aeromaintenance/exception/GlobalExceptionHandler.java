package com.aeromaintenance.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.common.ResponseBean;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔹 Handle validation errors (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBean<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(FieldError::getDefaultMessage)
                                .collect(Collectors.toList());

        String errorMessage = String.join(", ", errors);

        ResponseBean<String> responseBean = new ResponseBean<>("400", errorMessage, null);
        return new ResponseEntity<>(responseBean, HttpStatus.BAD_REQUEST);
    }

    // 🔹 Handle duplicate record exceptions
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ResponseBean<String>> handleDuplicateRecord(DuplicateRecordException ex) {
        ResponseBean<String> responseBean = new ResponseBean<>("409", ex.getMessage(), null);
        return new ResponseEntity<>(responseBean, HttpStatus.CONFLICT);
    }

    // 🔹 Handle IllegalArgumentException (generic bad input)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseBean<String>> handleIllegalArgument(IllegalArgumentException ex) {
        ResponseBean<String> responseBean = new ResponseBean<>("400", ex.getMessage(), null);
        return new ResponseEntity<>(responseBean, HttpStatus.BAD_REQUEST);
    }

    // 🔹 Fallback handler for unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBean<String>> handleGenericException(Exception ex) {
        ResponseBean<String> responseBean = new ResponseBean<>("500", "Internal Server Error: " + ex.getMessage(), null);
        return new ResponseEntity<>(responseBean, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

