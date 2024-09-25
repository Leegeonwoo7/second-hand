package com.secondhandplatform.user.service;

import com.secondhandplatform.common.exception.BadRequestException;
import com.secondhandplatform.common.exception.DuplicateException;
import com.secondhandplatform.common.exception.MailSendException;
import com.secondhandplatform.delivery.domain.Address;
import com.secondhandplatform.provider.CertificationCodeProvider;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.provider.TokenProvider;
import com.secondhandplatform.user.domain.Certification;
import com.secondhandplatform.user.domain.CertificationRepository;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import com.secondhandplatform.user.dto.request.*;
import com.secondhandplatform.user.dto.response.JoinResponse;
import com.secondhandplatform.user.dto.response.LoginResponse;
import com.secondhandplatform.user.dto.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    public UserResponse checkLoginIdAvailability(String username) {
        boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            throw new DuplicateException(USERNAME_DUPLICATE);
        }

        return UserResponse.builder()
                .message(UserResponse.USERNAME_OK)
                .build();
    }

    //이메일 중복여부 확인
    public UserResponse checkEmailAvailability(String email) {
        boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new DuplicateException(EMAIL_DUPLICATE);
        }

        return UserResponse.builder()
                .message(UserResponse.EMAIL_OK)
                .build();
    }

    // 이메일 인증번호를 전송함과 동시에 아이디 중복체크와 이메일 중복체크를 수행
    // 그럼 쿼리를 또 조회해야하는데... 그냥 stateless하게 클라이언트에 이메일 중복체크, 아이디 중복체크 여부를 준다면?
    public UserResponse sendCertificationCode(CertificationCodeRequest request) {
        String email = request.getEmail();

        String certificationCode = CertificationCodeProvider.createCertificationCode();
        //TODO 개발을 위해 이메일 비활성화
//        boolean isSuccess = emailProvider.sendMail(email, certificationCode);

//        if (!isSuccess) {
//            log.error("이메일 전송중 오류 발생");
//            throw new MailSendException(MAIL_SEND_FAIL);
//        }

        Certification certification = Certification.create(email, certificationCode);
        certificationRepository.save(certification);

        return UserResponse.builder()
                .message(UserResponse.EMAIL_SEND_OK)
                .build();
    }

    // 인증번호 검증
    public UserResponse certificationCheck(CertificationCodeCheckRequest request) {
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

        return UserResponse.builder()
                .message(UserResponse.CERTIFICATION_CHECK_OK)
                .build();
    }

    //회원가입
    public JoinResponse join(JoinRequest request) {
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

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

    //주소설정
    public void registerAddress(@RequestBody AddressRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_USER));

        String city = request.getCity();
        String zipcode = request.getZipcode();
        String detail = request.getDetail();
        Address address = new Address(city, zipcode, detail);
        user.registerAddress(address);
    }


}
