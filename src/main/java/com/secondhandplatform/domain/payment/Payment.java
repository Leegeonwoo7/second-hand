package com.secondhandplatform.domain.payment;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Payment extends BaseEntity {

    @Column(name = "payment_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus status;

    @Column(nullable = false)
    private int amount;
}
