package com.belajar.springbootfilter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ErrorException extends RuntimeException {

    private transient Map<String, Object> metadata;

    private final HttpStatus httpStatus;

    public ErrorException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ErrorException(Map<String, Object> metadata, HttpStatus httpStatus) {
        this.metadata = metadata;
        this.httpStatus = httpStatus;
    }

}
