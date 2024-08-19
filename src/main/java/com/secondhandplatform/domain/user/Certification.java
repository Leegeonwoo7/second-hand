package com.secondhandplatform.domain.user;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
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

    private Certification(String email, String certificationNumber) {
        this.email = email;
        this.certificationNumber = certificationNumber;
        this.expiresAt = LocalDateTime.now().plusMinutes(5);
    }

    public static Certification create(String email, String certificationNumber) {
        return new Certification(email, certificationNumber);
    }
}
