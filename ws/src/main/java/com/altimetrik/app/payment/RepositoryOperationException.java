package com.altimetrik.app.payment;

class RepositoryOperationException extends RuntimeException{
    RepositoryOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
