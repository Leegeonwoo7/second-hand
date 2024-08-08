package com.secondhandplatform.domain;

import jakarta.persistence.*;

@Entity
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] data;
}
