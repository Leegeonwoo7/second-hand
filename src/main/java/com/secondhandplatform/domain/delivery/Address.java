package com.secondhandplatform.domain.delivery;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String city;
    private String zipcode;
    private String detail;
}
