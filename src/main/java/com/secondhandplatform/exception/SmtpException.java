package com.secondhandplatform.exception;

public class SmtpException extends RuntimeException{

    public SmtpException(String message) {
        super(message);
    }

    public SmtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmtpException(Throwable cause) {
        super(cause);
    }

    protected SmtpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
