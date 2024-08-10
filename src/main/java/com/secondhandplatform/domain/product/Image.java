package com.secondhandplatform.domain.product;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Image extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] data;

    //TODO 현재 단방향 매핑, 양방향 매핑고려
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
