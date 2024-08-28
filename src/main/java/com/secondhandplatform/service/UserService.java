package com.secondhandplatform.service;

import com.secondhandplatform.domain.user.Certification;
import com.secondhandplatform.dto.user.request.CertificationCodeRequestDto;
import com.secondhandplatform.dto.user.response.CertificationCodeResponseDto;
import com.secondhandplatform.dto.user.response.EmailCheckResponseDto;
import com.secondhandplatform.dto.user.response.IdCheckResponseDto;
import com.secondhandplatform.provider.CertificationCodeProvider;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.repository.CertificationRepository;
import com.secondhandplatform.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailProvider emailProvider;
    private final CertificationRepository certificationRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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


    //    public SendCertificationResponse sendCertification(SendCertificationRequest request) {
//        String email = request.getEmail();
//        String certificationNumber = CertificationNumber.createCertificationNumber();
//
//        try {
//            emailProvider.sendMail(email, certificationNumber);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.warn("외부 이메일 전송 에러");
//            return SendCertificationResponse.fail();
//        }
//
//        Certification certification = Certification.create(email, certificationNumber);
//        certificationRepository.save(certification);
//
//        return SendCertificationResponse.success();
//    }
//
//    public CheckCertificationResponse checkCertification(CheckCertificationRequest request) {
//        String email = request.getEmail();
//        String certificationNumber = request.getCertificationNumber();
//        Certification findCertification = certificationRepository.findByEmail(email);
//
//        if (findCertification == null) {
//            log.warn("존재하지 않는 이메일: {}", email);
//            return CheckCertificationResponse.fail();
//        }
//
//        String targetEmail = findCertification.getEmail();
//        String targetCertificationNumber = findCertification.getCertificationNumber();
//        LocalDateTime expiresTime = findCertification.getExpiresAt();
//
//        if (LocalDateTime.now().isAfter(expiresTime)) {
//            log.warn("이메일 인증시간 초과: {}", expiresTime);
//            return CheckCertificationResponse.fail();
//        }
//
//        if (!(email.equals(targetEmail) && certificationNumber.equals(targetCertificationNumber))) {
//            return CheckCertificationResponse.fail();
//        }
//
//        return CheckCertificationResponse.success();
//    }
//
//    // TODO 회원가입 실패시 null을 반환해도 괜찮을까?
//    public UserResponse join(JoinRequest request) {
//        String certificationNumber = request.getCertificationNumber();
//        String email = request.getEmail();
//        String encodePassword = bCryptPasswordEncoder.encode(request.getPassword());
//
//        Certification findCertification = certificationRepository.findByEmail(email);
//        String targetEmail = findCertification.getEmail();
//        String targetCertificationNumber = findCertification.getCertificationNumber();
//
//        if (!(certificationNumber.equals(targetCertificationNumber) && email.equals(targetEmail))) {
//            log.warn("회원가입 정보가 일치하지 않습니다.");
//            return null;
//        }
//
//        User saveUser = userRepository.save(request.toEntity(encodePassword));
//        return UserResponse.of(saveUser);
//    }
//
//    public LoginResponse login(LoginRequest request) {
//        String loginId = request.getLoginId();
//        String password = request.getPassword();
//
//        return null;
//    }
}
