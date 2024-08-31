package com.secondhandplatform.service;

import com.secondhandplatform.domain.user.Certification;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.dto.user.request.CertificationCheckRequestDto;
import com.secondhandplatform.dto.user.request.CertificationCodeRequestDto;
import com.secondhandplatform.dto.user.request.CreateUserRequestDto;
import com.secondhandplatform.dto.user.request.LoginRequestDto;
import com.secondhandplatform.dto.user.response.*;
import com.secondhandplatform.provider.CertificationCodeProvider;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.provider.TokenProvider;
import com.secondhandplatform.repository.CertificationRepository;
import com.secondhandplatform.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public IdCheckResponseDto checkLoginIdAvailability(String loginId) {
        boolean isExist = userRepository.existsByLoginId(loginId); //이미존재하면 true반환

        if (isExist) { //이미 존재하는 아이디라면,
            log.debug("요청아이디: {}", loginId);
            return IdCheckResponseDto.builder()
                    .isDuplicate(true)
                    .message("이미 존재하는 아이디입니다.")
                    .loginId(loginId)
                    .build();
        }

        log.debug("[UserService] request loginId: {}", loginId);
        return IdCheckResponseDto.builder()
                .isDuplicate(false)
                .message("사용 가능한 아이디입니다.")
                .loginId(loginId)
                .build();
    }

    // 이메일 중복여부 확인
    public EmailCheckResponseDto checkEmailAvailability(String email) {
        boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            return EmailCheckResponseDto.builder()
                    .isExist(true)
                    .message("이미 존재하는 이메일 입니다.")
                    .email(email)
                    .build();
        }

        return EmailCheckResponseDto.builder()
                .isExist(false)
                .message("사용 가능한 이메일 입니다.")
                .email(email)
                .build();
    }

    // 이메일 인증번호를 전송함과 동시에 아이디 중복체크와 이메일 중복체크를 수행
    // TODO 반환타입을 인터페이스로?
    public CertificationCodeResponseDto sendCertificationCode(CertificationCodeRequestDto request) {
        String email = request.getEmail();
        String loginId = request.getLoginId();

        EmailCheckResponseDto emailResponse = checkEmailAvailability(email);
        IdCheckResponseDto idResponse = checkLoginIdAvailability(loginId);

        if (emailResponse.isExist() || idResponse.isDuplicate()) {
            return CertificationCodeResponseDto.builder()
                    .isSuccess(false)
                    .message("아이디 또는 이메일 검증 수행이 필요합니다.")
                    .email(email)
                    .loginId(loginId)
                    .build();
        }

        String code = CertificationCodeProvider.createCertificationNumber();

        boolean sendResult = emailProvider.sendMail(email, code);
        if (!sendResult) {
            return CertificationCodeResponseDto.builder()
                    .isSuccess(false)
                    .message("이메일 전송에 실패했습니다.")
                    .email(email)
                    .loginId(loginId)
                    .build();
        }

        Certification certificationEntity = Certification.create(email, code);
        certificationRepository.save(certificationEntity);

        return CertificationCodeResponseDto.builder()
                .isSuccess(true)
                .message("인증메일 전송 완료")
                .email(email)
                .loginId(loginId)
                .build();
    }

    // 인증번호 검증
    public CertificationCheckResponseDto certificationCheck(CertificationCheckRequestDto request) {
        String certificationCode = request.getCertificationCode();
        String email = request.getEmail();

        Certification targetCertification = certificationRepository.findByEmail(email);
        if (targetCertification == null) {
            return createResponse(false, "유효한 이메일이 아닙니다.");
        }

        String targetCode = targetCertification.getCertificationCode();
        String targetEmail = targetCertification.getEmail();
        LocalDateTime targetExpiresAt = targetCertification.getExpiresAt();

        if (!targetEmail.equals(email)) {
            return createResponse(false, "유효한 이메일이 아닙니다.");
        }

        if (!targetCode.equals(certificationCode)) {
            return createResponse(false, "잘못된 인증번호 입니다.");
        }

        if (targetExpiresAt.isBefore(LocalDateTime.now())) {
            return createResponse(false, "만료된 인증번호 입니다.");
        }

        log.debug("데이터베이스 Certification 엔티티 삭제");
        certificationRepository.delete(targetCertification);
        return createResponse(true, "인증 성공");
    }

    //회원가입
    public CreateUserResponseDto join(CreateUserRequestDto request) {
        String password = bCryptPasswordEncoder.encode(request.getPassword());
        log.debug("join user password: {}", password);

        User user = request.toEntity(request, password);
        log.debug("be save user: {}", user);

        User saveUser;

        try {
            saveUser = userRepository.save(user);
        } catch (Exception e) {
            return CreateUserResponseDto.builder()
                    .id(null)
                    .message("회원가입 실패")
                    .build();
        }

        return CreateUserResponseDto.builder()
                .id(saveUser.getId())
                .message("회원가입 성공")
                .build();
    }

    // 로그인
    public LoginResponseDto login(LoginRequestDto request) {
        String loginId = request.getLoginId();
        String password = request.getPassword();
        User user = userRepository.findByLoginId(loginId);

        if (user == null) {
            return LoginResponseDto.builder()
                    .token(null)
                    .message("존재하지 않는 아이디입니다.")
                    .build();
        }

        Long userId = user.getId();

        if (!(bCryptPasswordEncoder.matches(password, user.getPassword()))) {
            return LoginResponseDto
                    .builder()
                    .token(null)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
        }

        String token = tokenProvider.create(userId);
        return LoginResponseDto
                .builder()
                .token(token)
                .message("로그인 성공")
                .build();
    }



    // TODO
    // Certification,,EmailCheck,,,IdCheck,,,Response 모든 Response들이 비슷한 형태로 리턴되며 반복되는 코드 발생
    // 다형성으로 해결?
    private static CertificationCheckResponseDto createResponse(boolean isSuccess, String message) {
        return CertificationCheckResponseDto.builder()
                .isSuccess(isSuccess)
                .message(message)
                .build();
    }
}
