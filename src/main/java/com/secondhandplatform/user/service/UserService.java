package com.secondhandplatform.user.service;

import com.secondhandplatform.common.exception.DuplicateException;
import com.secondhandplatform.common.exception.MailSendException;
import com.secondhandplatform.provider.CertificationCodeProvider;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.provider.TokenProvider;
import com.secondhandplatform.user.domain.Certification;
import com.secondhandplatform.user.domain.CertificationRepository;
import com.secondhandplatform.user.domain.UserRepository;
import com.secondhandplatform.user.dto.request.CertificationCodeRequest;
import com.secondhandplatform.user.dto.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.secondhandplatform.common.exception.DuplicateException.*;

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
    public Response checkLoginIdAvailability(String username) {
        boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            throw new DuplicateException(USERNAME_DUPLICATE);
        }

        return Response.builder()
                .message(Response.USERNAME_OK)
                .build();
    }

    //이메일 중복여부 확인
    public Response checkEmailAvailability(String email) {
        boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new DuplicateException(EMAIL_DUPLICATE);
        }

        return Response.builder()
                .message(Response.EMAIL_OK)
                .build();
    }

    // 이메일 인증번호를 전송함과 동시에 아이디 중복체크와 이메일 중복체크를 수행
    // 그럼 쿼리를 또 조회해야하는데... 그냥 stateless하게 클라이언트에 이메일 중복체크, 아이디 중복체크 여부를 준다면?
    public Response sendCertificationCode(CertificationCodeRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();

        String certificationCode = CertificationCodeProvider.createCertificationCode();
        boolean isSuccess = emailProvider.sendMail(email, certificationCode);

        if (!isSuccess) {
            log.error("이메일 전송중 오류 발생");
            throw new MailSendException("이메일 전송중 오류 발생");
        }

        Certification certification = Certification.create(email, certificationCode);
        certificationRepository.save(certification);

        return Response.builder()
                .message(Response.EMAIL_SEND_OK)
                .build();
    }

    // 인증번호 검증
//    public CertificationCheckResponseDto certificationCheck(CertificationCheckRequestDto request) {
//
//    }

    //회원가입
//    public CreateUserResponseDto join(CreateUserRequestDto request) {
//
//    }

    // 로그인
//    public LoginResponseDto login(LoginRequestDto request) {
//
//    }


}
