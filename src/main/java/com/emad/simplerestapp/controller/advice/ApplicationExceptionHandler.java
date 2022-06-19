package com.emad.simplerestapp.controller.advice;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)//Or NOT_FOUND
    @ExceptionHandler(MasterEntityNotFoundException.class)
    public Map<String, String> handleMasterEntityNotFoundException(MasterEntityNotFoundException e) {
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Map<String, String> handleTypeNotSupportException(HttpMediaTypeNotSupportedException e) {
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", "please set Content-Type=application/json on request header;");
        return map;
    }

}
