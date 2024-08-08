package com.secondhandplatform.domain.delivery;

import lombok.Getter;

@Getter
public enum DeliverStatus {

    READY("배송 준비중"),
    IN_TRANSIT("배송중"),
    COMPLETE("배송 완료");

    private final String description;

    DeliverStatus(String description) {
        this.description = description;
    }
}
