package com.secondhandplatform.service.user.response;

import lombok.Getter;

@Getter
public class EmailCertificationResponseDto {

    private String email;
    private String message;
    private boolean duplicateId;
    private boolean certificationFail;

    private EmailCertificationResponseDto(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public static EmailCertificationResponseDto duplicateId(String email) {
        return new EmailCertificationResponseDto(email, "중복되는 아이디 아이디 중복확인을 먼저 진행하세요.");
    }

    public static EmailCertificationResponseDto mailSendFail(String email) {
        return new EmailCertificationResponseDto(email, "이메일 전송에 실패하였습니다.");
    }

    public static EmailCertificationResponseDto success(String email) {
        return new EmailCertificationResponseDto(email, "이메일 전송 성공.");
    }
}
