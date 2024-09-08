package com.secondhandplatform.user.service;

import com.secondhandplatform.common.exception.BadRequestException;
import com.secondhandplatform.common.exception.DuplicateException;
import com.secondhandplatform.provider.CertificationCodeProvider;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.user.domain.*;
import com.secondhandplatform.user.dto.request.CertificationCodeCheckRequest;
import com.secondhandplatform.user.dto.request.CertificationCodeRequest;
import com.secondhandplatform.user.dto.request.JoinRequest;
import com.secondhandplatform.user.dto.response.JoinResponse;
import com.secondhandplatform.user.dto.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    CertificationRepository certificationRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        certificationRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 중복확인을 시도한다.")
    void checkLoginIdAvailability() {
        // given
        String username = "memberA";

        //when
        Response response = userService.checkLoginIdAvailability(username);

        //then
        assertThat(response.getMessage()).isEqualTo(Response.USERNAME_OK);
    }

    @Test
    @DisplayName("존재하는 아이디로 중복확인을 시도한다.")
    void checkLoginIdAvailability2() {
        // given
        String username = "memberA";

        User user = User.builder()
                .username(username)
                .build();

        userRepository.save(user);

        //when //then
        assertThatThrownBy(() -> userService.checkLoginIdAvailability(username))
                .isInstanceOf(DuplicateException.class);
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 중복확인을 시도한다.")
    void checkEmailAvailability() {
        // given
        String email = "test@example.com";

        //when
        Response response = userService.checkEmailAvailability(email);

        //then
        assertThat(response.getMessage()).isEqualTo(Response.EMAIL_OK);
    }

    @Test
    @DisplayName("존재하는 이메일로 중복확인을 시도한다.")
    void checkEmailAvailability2() {
        // given
        String email = "test@example.com";

        User user = User.builder()
                .username("uesrA")
                .email(email)
                .build();

        userRepository.save(user);

        //when //then
        assertThatThrownBy(() -> userService.checkEmailAvailability(email))
                .isInstanceOf(DuplicateException.class);
    }
    
  @Test
  @DisplayName("인증번호 이메일 전송에 성공한다.")
  void sendCertificationCodeSuccess() {

  }

  @Test
  @DisplayName("이메일 검증에 성공한다")
  void certificationCheck() {
      //given
      String email = "test@example.com";
      String code = "1234";

      Certification certification = Certification.builder()
              .email(email)
              .certificationNumber(code)
              .build();

      Certification savedCertification = certificationRepository.save(certification);

      CertificationCodeCheckRequest request = new CertificationCodeCheckRequest(email, code);

      //when
      Response response = userService.certificationCheck(request);

      //then
      assertThat(response.getMessage()).isEqualTo(Response.CERTIFICATION_CHECK_OK);

      Optional<Certification> afterDeleteEntity = certificationRepository.findById(savedCertification.getId());
      assertThat(afterDeleteEntity).isEmpty();
  }

  @Test
  @DisplayName("잘못된 인증번호로 이메일 인증에 실패한다")
  void certificationCheckFail() {
      //given
      String email = "test@example.com";
      String code = "1234";

      Certification certification = Certification.builder()
              .email(email)
              .certificationNumber(code)
              .build();
      Certification savedCertification = certificationRepository.save(certification);

      CertificationCodeCheckRequest request = new CertificationCodeCheckRequest(email, "5432");

      //when //then
      assertThatThrownBy(() -> userService.certificationCheck(request))
              .isInstanceOf(BadRequestException.class)
              .hasMessage(BadRequestException.WRONG_CERTIFICATION_CODE);

  }

    @Test
    @DisplayName("인증요청한 이메일이 일치하지 않아 인증에 실패한다")
    void certificationCheckFail2() {
        //given
        String email = "test@example.com";
        String code = "1234";

        Certification certification = Certification.builder()
                .email(email)
                .certificationNumber(code)
                .build();
        Certification savedCertification = certificationRepository.save(certification);

        CertificationCodeCheckRequest request = new CertificationCodeCheckRequest("wrongEmail@example.com", code);

        //when //then
        assertThatThrownBy(() -> userService.certificationCheck(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(BadRequestException.WRONG_EMAIL);

    }

    @Test
    @DisplayName("회원가입에 성공한다.")
    void join() {
        //given
        LocalDate localDate = LocalDate.of(1998, 12, 12);

        JoinRequest req = JoinRequest.builder()
                .username("memberA")
                .password("1234")
                .phone("01012341234")
                .email("test@example.com")
                .userType(UserType.USER)
                .signupType(SignupType.APP)
                .name("nameEx")
                .birthday(localDate)
                .build();

        //when
        JoinResponse response = userService.join(req);

        //then
        assertThat(response.getUserId()).isEqualTo(1L);
    }
}