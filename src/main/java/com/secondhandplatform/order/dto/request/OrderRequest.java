package com.secondhandplatform.order.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRequest {
    // TODO 추후에 Payment도 넣어줘야함
    private Long buyerId;
    private Long productId;
    private int quantity;

}
