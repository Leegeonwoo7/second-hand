package com.secondhandplatform.review;

import com.secondhandplatform.user.domain.BaseEntity;
import com.secondhandplatform.order.domain.Order;
import com.secondhandplatform.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private int rating;

    private String comment;

    //TODO 양방향 연관관계 고려
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //TODO 양방향 연관관계 고려
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order oder;

    @Builder
    private Review(int rating, String comment, User user, Order oder) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.oder = oder;
    }
}
