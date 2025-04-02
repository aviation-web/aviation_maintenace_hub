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
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ResponseBean<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        List<String> errors = ex.getBindingResult()
	                                .getFieldErrors()
	                                .stream()
	                                .map(FieldError::getDefaultMessage)
	                                .collect(Collectors.toList());

	        String errorMessage = String.join(", ", errors);

	        // ✅ Convert 400 to String to match ResponseBean<String>
	        ResponseBean<String> responseBean = new ResponseBean<>("400", errorMessage, null);
	        
	        return new ResponseEntity<>(responseBean, HttpStatus.BAD_REQUEST);
	    }
}
