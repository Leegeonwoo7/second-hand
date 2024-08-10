package com.secondhandplatform.domain.review;

import com.secondhandplatform.domain.BaseEntity;
import com.secondhandplatform.domain.order.Order;
import com.secondhandplatform.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;

    private String comment;

    //TODO 양방향 연관관계 고려
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User user;

    //TODO 양방향 연관관계 고려
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order oder;
}
