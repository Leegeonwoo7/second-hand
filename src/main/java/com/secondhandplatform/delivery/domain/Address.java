package com.secondhandplatform.delivery.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Address address = (Address) object;
        return Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getZipcode(), address.getZipcode()) &&
                Objects.equals(getDetail(), address.getDetail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getZipcode(), getDetail());
    }
}