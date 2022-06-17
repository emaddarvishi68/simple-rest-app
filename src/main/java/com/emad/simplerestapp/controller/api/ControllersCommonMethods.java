package com.emad.simplerestapp.controller.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ControllersCommonMethods<T> {

    protected ResponseEntity<List<T>> returnResponseEntity(List<T> list) {
        return list.isEmpty() ? new ResponseEntity<>(new ArrayList<>(), new HttpHeaders(), HttpStatus.NO_CONTENT) : new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    protected ResponseEntity<Optional<T>> returnResponseEntity(Optional<T> t) {
        return t.isPresent() ? new ResponseEntity<>(t, new HttpHeaders(), HttpStatus.OK) : new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    protected ResponseEntity<Void> returnResponseEntity(HttpStatus httpStatus) {
        return new ResponseEntity<>(null, new HttpHeaders(), httpStatus);
    }

}
