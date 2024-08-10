package com.secondhandplatform.domain.payment;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

    @Builder
    private Payment(PaymentType type, PayStatus status, int amount) {
        this.type = type;
        this.status = status;
        this.amount = amount;
    }
}
