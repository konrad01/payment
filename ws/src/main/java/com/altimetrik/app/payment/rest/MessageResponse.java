package com.altimetrik.app.payment.rest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class MessageResponse {
    private final int status;
    private final String message;

    static MessageResponse error(HttpStatus status, String message) {
        return new MessageResponse(status.value(), message);
    }

    static MessageResponse ok(String message) {
        return new MessageResponse(HttpStatus.OK.value(), message);
    }
}
