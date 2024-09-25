package com.secondhandplatform.payment.dto.request;

import lombok.Getter;

@Getter
public class OrderForm {

    private String itemName;
    private int totalAmount;
}
