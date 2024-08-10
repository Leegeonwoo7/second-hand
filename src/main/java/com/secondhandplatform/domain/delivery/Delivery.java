package com.secondhandplatform.domain.delivery;

import com.secondhandplatform.domain.BaseEntity;
import com.secondhandplatform.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery extends BaseEntity {

    @Column(name = "delivery_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliverStatus deliverStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
