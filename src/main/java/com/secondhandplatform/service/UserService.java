package com.secondhandplatform.service;

import com.secondhandplatform.dto.user.request.IdCheckRequestDto;
import com.secondhandplatform.dto.user.response.IdCheckResponseDto;
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
    public IdCheckResponseDto checkLoginIdAvailability(IdCheckRequestDto request) {
        String loginId = request.getLoginId();
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
