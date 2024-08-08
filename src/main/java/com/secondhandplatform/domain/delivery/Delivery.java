package com.secondhandplatform.domain.delivery;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Column(name = "delivery_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "deliver_status")
    private DeliverStatus deliverStatus;
}
