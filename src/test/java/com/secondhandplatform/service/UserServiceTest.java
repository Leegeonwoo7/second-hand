package com.secondhandplatform.service;

import static org.mockito.Mockito.*;

import com.secondhandplatform.dto.user.response.EmailCheckResponseDto;
import com.secondhandplatform.dto.user.response.IdCheckResponseDto;
import com.secondhandplatform.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
}