package com.emad.simplerestapp.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public abstract class ControllersCommonMethods<T> {
    protected ResponseEntity<Optional<T>> returnResponseEntityForDeleteOrUpdate(Optional<T> t) {
        return t.isPresent() ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
