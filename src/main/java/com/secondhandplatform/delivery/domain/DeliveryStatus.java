package com.secondhandplatform.delivery.domain;

import lombok.Getter;

@Getter
public enum DeliveryStatus {

    READY("배송 준비중"),
    IN_TRANSIT("배송중"),
    COMPLETE("배송 완료");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }
}
