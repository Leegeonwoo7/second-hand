package com.secondhandplatform.domain.favorite;

import com.secondhandplatform.domain.BaseEntity;
import com.secondhandplatform.domain.product.Product;
import com.secondhandplatform.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Favorite extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO 현재 단방향 매핑, 양방향 매핑고려
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //TODO 현재 단방향 매핑, 양방향 매핑고려
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
