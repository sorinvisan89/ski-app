package com.demo.skiapp.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private HttpStatus status;

    public ServiceException(String msg, HttpStatus status){
        super(msg);
        this.status = status;
    }
}
