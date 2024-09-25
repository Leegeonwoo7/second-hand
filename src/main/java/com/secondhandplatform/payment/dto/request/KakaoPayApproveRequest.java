package com.secondhandplatform.payment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoPayApproveRequest {
    private String cid;
    private String tid;
    @JsonProperty("partner_order_id")
    private String partnerOrderId;
    @JsonProperty("partner_user_id")
    private String partnerUserId;
    @JsonProperty("pg_token")
    private String pgToken;

    public static KakaoPayApproveRequest createApproveRequest(String tid, String pgToken) {
        return KakaoPayApproveRequest.builder()
                .cid("TC0ONETIME")
                .tid(tid)
                .partnerOrderId("1234567890")
                .partnerUserId("testUserId123")
                .pgToken(pgToken)
                .build();
    }
}
