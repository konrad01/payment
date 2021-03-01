package com.altimetrik.app.exceptions;

import lombok.Data;

@Data
public class MessageResponse {
    private final int status;
    private final String message;
}
