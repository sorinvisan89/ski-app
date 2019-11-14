package com.demo.skiapp.exception;

import com.demo.skiapp.domain.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Component
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<ErrorDetails> handleProbeException(ServiceException exception, WebRequest webRequest) {
        ErrorDetails error = ErrorDetails.builder()
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.ok(error);
    }
}