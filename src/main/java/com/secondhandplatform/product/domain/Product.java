package com.secondhandplatform.product.domain;

import com.secondhandplatform.user.domain.BaseEntity;
import com.secondhandplatform.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "product_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "product_name", length = 50, nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "product_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "selling_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SellingStatus sellingStatus;

    @Builder
    private Product(String name, String description, int price, int quantity, Category category, ProductStatus productStatus, SellingStatus sellingStatus) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.productStatus = productStatus;
        this.sellingStatus = sellingStatus;
    }

    //연관관계 편의 메서드
    //TODO 양방향 매핑시 메서드 수정필요
    public void registerBy(User user) {
        this.user = user;
    }
}
