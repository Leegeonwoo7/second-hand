package com.secondhandplatform.domain.order;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "order_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_price", nullable = false)
    private int price;

    @Column(name = "order_quantity", nullable = false)
    private int quantity;

//    private Address
}
