package com.secondhandplatform.domain.order;

import com.secondhandplatform.domain.BaseEntity;
import com.secondhandplatform.domain.delivery.Delivery;
import com.secondhandplatform.domain.payment.Payment;
import com.secondhandplatform.domain.product.Product;
import com.secondhandplatform.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "order_id")
    private Payment payment;

    //TODO 양방향 연관관계 고려
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //TODO 양방향 연관관계 고려
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //TODO 양방향 연관관계 고려
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private Order(OrderStatus orderStatus, int price, int quantity, Payment payment, Delivery delivery, User user, Product product) {
        this.orderStatus = orderStatus;
        this.price = price;
        this.quantity = quantity;
        this.payment = payment;
        this.delivery = delivery;
        this.user = user;
        this.product = product;
    }
}
