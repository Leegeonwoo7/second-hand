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
    private String certificationCode;

    private LocalDateTime expiresAt;

    private Certification(String email, String certificationNumber) {
        this.email = email;
        this.certificationCode = certificationNumber;
        this.expiresAt = LocalDateTime.now().plusMinutes(5);
    }

    /*
     * 테스트용 생성자
     */
    @Builder
    private Certification(String email, String certificationNumber, LocalDateTime expiresAt) {
        this.email = email;
        this.certificationCode = certificationNumber;
        this.expiresAt = expiresAt;
    }

    public static Certification create(String email, String certificationNumber) {
        return new Certification(email, certificationNumber);
    }
}
