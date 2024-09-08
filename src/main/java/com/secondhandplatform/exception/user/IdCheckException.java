package com.secondhandplatform.exception.user;

public class IdCheckException extends RuntimeException{
    public IdCheckException() {
        super();
    }

    public IdCheckException(String message) {
        super(message);
    }

    public IdCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdCheckException(Throwable cause) {
        super(cause);
    }

    protected IdCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
