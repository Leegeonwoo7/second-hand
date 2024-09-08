package com.secondhandplatform.user.service;

import com.secondhandplatform.dto.user.request.CertificationCheckRequestDto;
import com.secondhandplatform.dto.user.request.CertificationCodeRequestDto;
import com.secondhandplatform.dto.user.request.CreateUserRequestDto;
import com.secondhandplatform.dto.user.request.LoginRequestDto;
import com.secondhandplatform.dto.user.response.*;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.provider.TokenProvider;
import com.secondhandplatform.user.domain.CertificationRepository;
import com.secondhandplatform.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailProvider emailProvider;
    private final CertificationRepository certificationRepository;

    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //아이디 중복체크
    public UserResponseDto checkLoginIdAvailability(String loginId) {

    }

    // 이메일 중복여부 확인
    public EmailCheckResponseDto checkEmailAvailability(String email) {

    }

    // 이메일 인증번호를 전송함과 동시에 아이디 중복체크와 이메일 중복체크를 수행
    public CertificationCodeResponseDto sendCertificationCode(CertificationCodeRequestDto request) {

    }

    // 인증번호 검증
    public CertificationCheckResponseDto certificationCheck(CertificationCheckRequestDto request) {

    }

    //회원가입
    public CreateUserResponseDto join(CreateUserRequestDto request) {

    }

    // 로그인
    public LoginResponseDto login(LoginRequestDto request) {

    }


}
