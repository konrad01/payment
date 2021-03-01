package com.altimetrik.app.payment.repository;

class RepositoryOperationException extends RuntimeException{
    RepositoryOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
