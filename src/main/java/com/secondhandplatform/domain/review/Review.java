package com.secondhandplatform.domain.review;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;

    private String comment;
}
