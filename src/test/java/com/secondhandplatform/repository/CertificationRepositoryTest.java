package com.secondhandplatform.repository;

import com.secondhandplatform.domain.user.Certification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CertificationRepositoryTest {

    @Autowired
    private CertificationRepository certificationRepository;

    @Test
    @Order(1)
    @DisplayName("이메일주소로 조회한다")
    void findByEmail() {
        // given
        String email = "testCertification@example.com";
        Certification certification = Certification.create(email, "1234");
        certificationRepository.save(certification);

        //when
        Certification findCertification = certificationRepository.findByEmail(email);

        //then
        assertThat(findCertification.getEmail()).isEqualTo(email);
    }
}