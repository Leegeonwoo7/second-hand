package com.secondhandplatform.order.domain;

import com.secondhandplatform.user.domain.BaseEntity;
import com.secondhandplatform.delivery.domain.Delivery;
import com.secondhandplatform.payment.domain.Payment;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.user.domain.User;
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

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_price")
    private int totalPrice;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Payment payment;

    //TODO 양방향 연관관계 고려
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //TODO 양방향 연관관계 고려
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    //TODO 양방향 연관관계 고려
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private Order(OrderStatus orderStatus, int totalPrice, Payment payment, Delivery delivery, User buyer, User seller, Product product) {
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.delivery = delivery;
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
    }

    public static Order createOrder(User buyer, User seller, Product product, Payment payment, Delivery delivery) {
        int totalPrice = product.getPrice();

        return Order.builder()
                .buyer(buyer)
                .seller(seller)
                .product(product)
                .orderStatus(OrderStatus.INIT)
                .payment(payment)
                .delivery(delivery)
                .totalPrice(totalPrice)
                .build();
    }
}
