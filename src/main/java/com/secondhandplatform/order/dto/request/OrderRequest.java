package com.secondhandplatform.order.dto.request;

import com.secondhandplatform.delivery.domain.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRequest {
    // TODO 추후에 Payment도 넣어줘야함
    private Long buyerId;
    private Long productId;
    private int quantity;
    private Address address;
}
