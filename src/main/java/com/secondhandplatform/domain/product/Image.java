package com.secondhandplatform.domain.product;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] data;

    //TODO 현재 단방향 매핑, 양방향 매핑고려
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private Image(byte[] data, Product product) {
        this.data = data;
        this.product = product;
    }
}
