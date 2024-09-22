package com.secondhandplatform.delivery.domain;

import com.secondhandplatform.user.domain.BaseEntity;
import com.secondhandplatform.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Delivery extends BaseEntity {

    @Column(name = "delivery_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Delivery(Address address, DeliveryStatus deliveryStatus, User user) {
        this.address = address;
        this.deliveryStatus = deliveryStatus;
        this.user = user;
    }
}
