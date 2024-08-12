package com.secondhandplatform.domain.user;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Certification extends BaseEntity {

    @Column(name = "certification_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(length = 4)
    private String certificationNumber;

    private LocalDateTime expiresAt;

    @Builder
    private Certification(String email, String certificationNumber, LocalDateTime expiresAt) {
        this.email = email;
        this.certificationNumber = certificationNumber;
        this.expiresAt = expiresAt;
    }


}
