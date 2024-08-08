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
}
