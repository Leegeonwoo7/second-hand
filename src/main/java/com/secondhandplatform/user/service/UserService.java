package com.secondhandplatform.user.service;

import com.secondhandplatform.common.exception.BadRequestException;
import com.secondhandplatform.common.exception.DuplicateException;
import com.secondhandplatform.common.exception.MailSendException;
import com.secondhandplatform.provider.CertificationCodeProvider;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.provider.TokenProvider;
import com.secondhandplatform.user.domain.Certification;
import com.secondhandplatform.user.domain.CertificationRepository;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import com.secondhandplatform.user.dto.request.CertificationCodeCheckRequest;
import com.secondhandplatform.user.dto.request.CertificationCodeRequest;
import com.secondhandplatform.user.dto.request.JoinRequest;
import com.secondhandplatform.user.dto.request.LoginRequest;
import com.secondhandplatform.user.dto.response.JoinResponse;
import com.secondhandplatform.user.dto.response.LoginResponse;
import com.secondhandplatform.user.dto.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.secondhandplatform.common.exception.BadRequestException.*;
import static com.secondhandplatform.common.exception.DuplicateException.*;
import static com.secondhandplatform.common.exception.MailSendException.*;

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
            throw new MailSendException(MAIL_SEND_FAIL);
        }

        Certification certification = Certification.create(email, certificationCode);
        certificationRepository.save(certification);

        return Response.builder()
                .message(Response.EMAIL_SEND_OK)
                .build();
    }

    // 인증번호 검증
    public Response certificationCheck(CertificationCodeCheckRequest request) {
        String targetCode = request.getCertificationCode();
        String targetEmail = request.getEmail();

        Certification findCertification = certificationRepository.findByEmail(targetEmail);
        if (findCertification == null) {
            throw new BadRequestException(WRONG_EMAIL);
        }

        String findEmail = findCertification.getEmail();
        String findCode = findCertification.getCertificationCode();

        if (!(targetCode.equals(findCode) && targetEmail.equals(findEmail))) {
            throw new BadRequestException(WRONG_CERTIFICATION_CODE);
        }

        certificationRepository.delete(findCertification);

        return Response.builder()
                .message(Response.CERTIFICATION_CHECK_OK)
                .build();
    }

    //회원가입
    public JoinResponse join(JoinRequest request) {
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        if (encodedPassword.length() < 30) {
            log.error("비밀번호 인코딩이 잘못됨");
            throw new RuntimeException("비밀번호 인코딩이 잘못됨");
        }

        User user = request.toEntity();
        log.info("비밀번호: {}", user.getPassword());

        User savedUser = userRepository.save(user);

        return JoinResponse.from(savedUser);
    }

    // 로그인
    public LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        User findUser = userRepository.findByUsername(username);
        if (findUser == null) {
            throw new BadRequestException(NOT_EXIST_USER);
        }

        boolean isMatches = bCryptPasswordEncoder.matches(password, findUser.getPassword());
        System.out.println("password = " + password);
        System.out.println("findUser.getPassword() = " + findUser.getPassword());
        if (!isMatches) {
            throw new BadRequestException(WRONG_LOGIN_INFO);
        }

        String token = tokenProvider.create(findUser.getId());
        log.info("create token: {}", token);

        return LoginResponse.builder()
                .id(findUser.getId())
                .token(token)
                .name(findUser.getName())
                .build();
    }


}
