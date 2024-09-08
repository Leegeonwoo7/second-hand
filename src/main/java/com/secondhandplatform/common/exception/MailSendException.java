package com.secondhandplatform.common.exception;

public class MailSendException extends RuntimeException{

    public static final String MAIL_SEND_FAIL = "이메일 전송중 오류 발생";

    public MailSendException(String message) {
        super(message);
    }
}
