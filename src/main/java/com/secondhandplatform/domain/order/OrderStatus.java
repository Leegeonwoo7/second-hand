package com.secondhandplatform.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    /**
     * 주문 생성 - 구매자가 판매자에게 주문을 요청하여 거래를 시작한 상태
     * 입금 완료 - 구매자가 판매자에게 입금을 완료한 상태
     * 배송 중 - 판매자가 구매자에게 배송신청을 완료한 상태
     * 배송 완료 - 구매자가 판매자의 상품을 정상적으로 받은 상태
     * 주문 취소 - 구매자가 "배송 중" 상태 이전에 주문을 취소한 상태
     * 반품 신청 - 구매자가 "배송 완료" 상태에서 반품을 신청한 상태
     * 반품 완료 - 판매자에게 정상적으로 반품상품이 도착한 상태
     */
    INIT("주문 생성"),
    CONFIRMED("입금 완료"),
    DELIVERED("배송 중"),
    DELIVERY_COMPLETE("배송 완료"),
    CANCELLED("주문 취소"),
    RETURNED("반품 신청"),
    RETURN_COMPLETE("반품 완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
