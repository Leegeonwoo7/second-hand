package com.secondhandplatform.favorite.domain;

import com.secondhandplatform.user.domain.BaseEntity;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Favorite extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    //TODO 현재 단방향 매핑, 양방향 매핑고려
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //TODO 현재 단방향 매핑, 양방향 매핑고려
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private Favorite(User user, Product product) {
        this.user = user;
        this.product = product;
    }
}
