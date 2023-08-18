package com.BCB.bcb.domain.dbo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.exception.ApiException;

@ControllerAdvice
@RestController
public class ExceptionControllerAdvice {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        
        String errorMessage = "Server error : " + e.getMessage();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleExceptionApi(ApiException e) {
        String errorMessage = "Error : " + e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleExceptionRunTime(RuntimeException e) {
        String errorMessage = "Error : " + e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    
}
