package com.management.project.handelexceptions;

import com.management.project.responses.commons.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@CrossOrigin
public class AppExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleException(Exception e) {
        return new ResponseEntity<>( new CommonResponse<Object>("500", "Error API not found", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new CommonResponse<>("404", "Error API not found", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<CommonResponse<Object>> handleValidationException(ValidateException e) {
        return new ResponseEntity<>(new CommonResponse<Object>("400", "Error Invalidate request", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
