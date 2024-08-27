package com.secondhandplatform.service;

import static org.mockito.Mockito.*;

import com.secondhandplatform.dto.user.request.IdCheckRequestDto;
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

        IdCheckRequestDto request = IdCheckRequestDto.builder()
                .loginId(nonExistId)
                .build();

        when(userRepository.existsByLoginId(nonExistId)).thenReturn(false);

        //when
        IdCheckResponseDto idCheckResponseDto = userService.checkLoginIdAvailability(request);

        //then
        assertThat(idCheckResponseDto.getLoginId()).isEqualTo("test");
        assertThat(idCheckResponseDto.isSuccess()).isTrue();
        assertThat(idCheckResponseDto.getMessage()).isEqualTo("사용 가능한 아이디입니다.");
    }

    @Test
    @Order(2)
    @DisplayName("로그인 아이디가 중복된다")
    void checkLoginIdAvailabilityFail() {
        // given
        String existLoginId = "existId";
        IdCheckRequestDto request = IdCheckRequestDto.builder()
                .loginId(existLoginId)
                .build();

        Mockito.when(userRepository.existsByLoginId(existLoginId)).thenReturn(true);

        // when
        IdCheckResponseDto idCheckResponseDto = userService.checkLoginIdAvailability(request);

        // then
        assertThat(idCheckResponseDto.isSuccess()).isFalse();
        assertThat(idCheckResponseDto.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
        assertThat(idCheckResponseDto.getLoginId()).isEqualTo(existLoginId);
    }
}