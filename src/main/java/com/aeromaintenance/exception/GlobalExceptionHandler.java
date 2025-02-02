package com.aeromaintenance.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<String> handleValidationExceptions(javax.validation.ConstraintViolationException ex) {
        StringBuilder message = new StringBuilder();
        ex.getConstraintViolations().forEach(violation -> {
            message.append(violation.getMessage()).append("\n");
        });
        return ResponseEntity.badRequest().body(message.toString());
    }

}
