package com.secondhandplatform.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressRequest {

    private Long userId;
    private String city;
    private String zipcode;
    private String detail;

}
