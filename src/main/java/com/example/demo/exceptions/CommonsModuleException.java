package com.example.demo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonsModuleException extends Exception {
    private final HttpStatus status;

    public CommonsModuleException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static CommonsModuleException badRequest(String message) {
        return new CommonsModuleException(message, HttpStatus.BAD_REQUEST);
    }
}
