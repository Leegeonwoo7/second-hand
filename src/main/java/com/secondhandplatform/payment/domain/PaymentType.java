package com.secondhandplatform.payment.domain;

import lombok.Getter;

@Getter
public enum PaymentType {
    CASH("계좌이체"),
    CARD("카드결제");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

}
