package com.example.taskmanager.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException{

    private final HttpStatus code;

    private final String message;

    public CommonException(HttpStatus code, String message){
        this.code = code;
        this.message = message;
    }
}
