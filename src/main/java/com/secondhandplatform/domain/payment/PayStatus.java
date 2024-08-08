package com.secondhandplatform.domain.payment;

import lombok.Getter;

@Getter
public enum PayStatus {
    WAITING("결제대기"),
    COMPLETE("결제완료");


    private final String description;

    PayStatus(String description) {
        this.description = description;
    }
}
