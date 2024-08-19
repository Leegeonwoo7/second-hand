package com.secondhandplatform.service.user;

import com.secondhandplatform.api.request.user.DuplicateLoginIdRequest;
import com.secondhandplatform.api.request.user.SendCertificationRequest;
import com.secondhandplatform.domain.user.Certification;
import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import com.secondhandplatform.repository.CertificationRepository;
import com.secondhandplatform.service.user.response.DuplicateLoginIdResponse;
import com.secondhandplatform.service.user.response.SendCertificationResponse;
import org.junit.jupiter.api.*;
import com.secondhandplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        certificationRepository.deleteAllInBatch();
    }

    @Test
    @Order(1)
    @DisplayName("로그인 아이디 중복체크결과가 중복이다")
    void validateLoginId_duplicate() {
        // given
        String loginId = "userA";
        LocalDate birthday = LocalDate.of(1998, 6, 2);
        User userA = createUser(loginId, "1234", "userA@email.com", "01012341234", birthday, UserType.USER, SignupType.APP, false);
        userRepository.save(userA);

        DuplicateLoginIdRequest request = DuplicateLoginIdRequest.builder()
                .loginId(loginId)
                .build();

        //when
        DuplicateLoginIdResponse response = userService.validateLoginId(request);

        //then
        assertThat(response).isInstanceOf(DuplicateLoginIdResponse.class);
        assertThat(response.getDuplicate()).isTrue();
    }

    @Test
    @Order(2)
    @DisplayName("로그인 아이디 중복체크결과가 중복이 아니다")
    void validateLoginId_notDuplicate() {
        // given
        String loginId = "userA";
        LocalDate birthday = LocalDate.of(1998, 6, 2);
        User userA = createUser(loginId, "1234", "userA@email.com", "01012341234", birthday, UserType.USER, SignupType.APP, false);
        userRepository.save(userA);

        DuplicateLoginIdRequest request = DuplicateLoginIdRequest.builder()
                .loginId("newLoginId")
                .build();

        //when
        DuplicateLoginIdResponse response = userService.validateLoginId(request);

        //then
        assertThat(response).isInstanceOf(DuplicateLoginIdResponse.class);
        assertThat(response.getDuplicate()).isFalse();
    }
    
    @Test
    @Order(3)
    @DisplayName("인증 이메일 요청시 이메일 전송과 동시에 해당 인증정보를 저장한다")
    void sendCertification() {
        // given
        String email = "test@example.com";
        SendCertificationRequest request = SendCertificationRequest.builder()
                .email(email)
                .build();

        //when
        SendCertificationResponse response = userService.sendCertification(request);
        Certification findCertification = certificationRepository.findByEmail(email);

        //then
        assertThat(response.isSuccess()).isTrue();
        assertThat(findCertification.getEmail()).isEqualTo(email);
    }

    private static User createUser(
            String loginId,
            String password,
            String email,
            String phone,
            LocalDate birthday,
            UserType userType,
            SignupType signupType,
            boolean emailVerified
    ) {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .phone(phone)
                .birthday(birthday)
                .userType(userType)
                .signupType(signupType)
                .emailVerified(emailVerified)
                .build();
    }
}