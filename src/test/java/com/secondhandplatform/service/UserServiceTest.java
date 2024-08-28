package com.secondhandplatform.service;

import static org.mockito.Mockito.*;

import com.secondhandplatform.domain.user.Certification;
import com.secondhandplatform.dto.user.request.CertificationCheckRequestDto;
import com.secondhandplatform.dto.user.request.CertificationCodeRequestDto;
import com.secondhandplatform.dto.user.response.CertificationCheckResponseDto;
import com.secondhandplatform.dto.user.response.CertificationCodeResponseDto;
import com.secondhandplatform.dto.user.response.EmailCheckResponseDto;
import com.secondhandplatform.dto.user.response.IdCheckResponseDto;
import com.secondhandplatform.provider.CertificationCodeProvider;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.repository.CertificationRepository;
import com.secondhandplatform.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailProvider emailProvider;

    @Mock
    private CertificationRepository certificationRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void tearDown() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("로그인 아이디가 중복되지 않는다")
    void checkLoginIdAvailabilitySuccess() {
        // given
        String nonExistId = "test";

        when(userRepository.existsByLoginId(nonExistId)).thenReturn(false);

        //when
        IdCheckResponseDto idCheckResponseDto = userService.checkLoginIdAvailability(nonExistId);

        //then
        assertThat(idCheckResponseDto.getLoginId()).isEqualTo("test");
        assertThat(idCheckResponseDto.isDuplicate()).isFalse();
        assertThat(idCheckResponseDto.getMessage()).isEqualTo("사용 가능한 아이디입니다.");
    }

    @Test
    @Order(2)
    @DisplayName("로그인 아이디가 중복된다")
    void checkLoginIdAvailabilityFail() {
        // given
        String existLoginId = "existId";

        Mockito.when(userRepository.existsByLoginId(existLoginId)).thenReturn(true);

        // when
        IdCheckResponseDto idCheckResponseDto = userService.checkLoginIdAvailability(existLoginId);

        // then
        assertThat(idCheckResponseDto.isDuplicate()).isTrue();
        assertThat(idCheckResponseDto.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
        assertThat(idCheckResponseDto.getLoginId()).isEqualTo(existLoginId);
    }

    @Test
    @Order(3)
    @DisplayName("이메일이 중복되지 않는다")
    void checkEmailAvailabilitySuccess() {
        // given
        String nonExistEmail = "test@example.com";

        when(userRepository.existsByEmail(nonExistEmail)).thenReturn(false);

        //when
        EmailCheckResponseDto emailCheckResponseDto = userService.checkEmailAvailability(nonExistEmail);

        //then
        assertThat(emailCheckResponseDto.getEmail()).isEqualTo(nonExistEmail);
        assertThat(emailCheckResponseDto.isExist()).isFalse();
        assertThat(emailCheckResponseDto.getMessage()).isEqualTo("사용 가능한 이메일 입니다.");
    }

    @Test
    @Order(4)
    @DisplayName("이미 존재하는 이메일이다")
    void checkEmailAvailabilityFail() {
        // given
        String existEmail = "exist@example.com";

        when(userRepository.existsByEmail(existEmail)).thenReturn(true);

        //when
        EmailCheckResponseDto emailCheckResponseDto = userService.checkEmailAvailability(existEmail);

        //then
        assertThat(emailCheckResponseDto.getEmail()).isEqualTo(existEmail);
        assertThat(emailCheckResponseDto.isExist()).isTrue();
        assertThat(emailCheckResponseDto.getMessage()).isEqualTo("이미 존재하는 이메일 입니다.");
    }

    @Test
    @Order(5)
    @DisplayName("이메일 인증 전송을 성공한다")
    void sendEmailSuccess() {
        // given
        String email = "test@example.com";
        String loginId = "loginId";

        CertificationCodeRequestDto request = CertificationCodeRequestDto.builder()
                .email(email)
                .loginId(loginId)
                .build();

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByLoginId(loginId)).thenReturn(false);

        try (MockedStatic<CertificationCodeProvider> mockedStatic = mockStatic(CertificationCodeProvider.class)) {
            mockedStatic.when(CertificationCodeProvider::createCertificationNumber).thenReturn("1234");

            when(emailProvider.sendMail(email, "1234")).thenReturn(true);

            // when
            CertificationCodeResponseDto response = userService.sendCertificationCode(request);

            // then
            assertThat(response.isSuccess()).isTrue();
        }

    }

    @Test
    @Order(6)
    @DisplayName("이메일 인증 전송을 실패한다")
    void sendEmailFail() {
        // given
        String email = "test@example.com";
        String loginId = "loginId";

        CertificationCodeRequestDto request = CertificationCodeRequestDto.builder()
                .email(email)
                .loginId(loginId)
                .build();

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByLoginId(loginId)).thenReturn(false);

        try (MockedStatic<CertificationCodeProvider> mockedStatic = mockStatic(CertificationCodeProvider.class)) {
            mockedStatic.when(CertificationCodeProvider::createCertificationNumber).thenReturn("1234");

            // 이메일 전송 실패
            when(emailProvider.sendMail(email, "1234")).thenReturn(false);

            // when
            CertificationCodeResponseDto response = userService.sendCertificationCode(request);

            // then
            assertThat(response.isSuccess()).isFalse();
        }
    }

    @Test
    @Order(7)
    @DisplayName("인증번호 검증에 성공한다.")
    void certificationCheckSuccess() {
        // given
        String email = "test@example.com";
        String certificationCode = "1234";

        CertificationCheckRequestDto request = CertificationCheckRequestDto.builder()
                .email(email)
                .certificationCode(certificationCode)
                .build();

        Certification certification = Certification.create(email, certificationCode);

        when(certificationRepository.findByEmail(email)).thenReturn(certification);

        //when
        CertificationCheckResponseDto response = userService.certificationCheck(request);

        //then
        System.out.println(certification.getExpiresAt());
        System.out.println(response.getMessage());
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("인증 성공");

        verify(certificationRepository, times(1)).delete(certification);
    }

    @Test
    @Order(8)
    @DisplayName("유효하지 않은 이메일로 검증에 실패한다.")
    void certificationCheckFalse() {
        // given
        String email = "test@example.com";
        String certificationCode = "1234";

        CertificationCheckRequestDto request = CertificationCheckRequestDto.builder()
                .email(email)
                .certificationCode(certificationCode)
                .build();

        Certification certification = Certification.create(email, certificationCode);

        when(certificationRepository.findByEmail(email)).thenReturn(null);

        //when
        CertificationCheckResponseDto response = userService.certificationCheck(request);

        //then
        assertThat(response.isSuccess()).isFalse();
    }

    //TODO 만료시간을 어떻게 임의로 변경하지??
    @Test
    @Order(9)
    @DisplayName("인증시간을 초과하여 검증에 실패한다.")
    void certificationCheckFail2() {
        // given
        String email = "test@example.com";
        String certificationCode = "1234";

        CertificationCheckRequestDto request = CertificationCheckRequestDto.builder()
                .email(email)
                .certificationCode(certificationCode)
                .build();

        Certification certification = Certification.create(email, certificationCode);

        when(certificationRepository.findByEmail(email)).thenReturn(certification);

        //when


        //then
    }


}