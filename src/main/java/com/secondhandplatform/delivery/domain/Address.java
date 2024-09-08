package com.secondhandplatform.delivery.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Address {

    private String city;
    private String zipcode;
    private String detail;

    public Address(String city, String zipcode, String detail) {
        this.city = city;
        this.zipcode = zipcode;
        this.detail = detail;
    }
}