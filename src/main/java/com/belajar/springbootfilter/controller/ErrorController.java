package com.belajar.springbootfilter.controller;

import com.belajar.springbootfilter.exception.ErrorException;
import com.belajar.springbootfilter.model.Response;
import com.belajar.springbootfilter.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = new HashMap<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String field = fieldError.getField();

            if (!errors.containsKey(field)) {
                errors.put(field, new ArrayList<>());
            }

            errors.get(field).add(fieldError.getDefaultMessage());
        }

        Response<Void> response = ResponseUtil.build(null, errors, null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<Response<Void>> handleErrorException(ErrorException e) {
        log.error("Error", e);

        Response<Void> response = ResponseUtil.build(null, null, e.getMetadata(), e.getHttpStatus());

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleException(Exception e) {
        log.error("Error", e);

        Response<Void> response = ResponseUtil.build(null, null, null, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
