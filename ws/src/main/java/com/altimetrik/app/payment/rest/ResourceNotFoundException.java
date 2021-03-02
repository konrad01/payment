package com.altimetrik.app.payment.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {

    ResourceNotFoundException(String message) {
        super(message);
    }
}
