package com.secondhandplatform.common.exception;

public class MailSendException extends RuntimeException{

    public MailSendException(String message) {
        super(message);
    }
}
