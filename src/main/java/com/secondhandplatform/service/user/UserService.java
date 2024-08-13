package com.secondhandplatform.service.user;

import com.secondhandplatform.api.request.user.EmailCertificationRequest;
import com.secondhandplatform.api.request.user.IdCheckRequest;
import com.secondhandplatform.api.request.user.SignupRequest;
import com.secondhandplatform.domain.user.Certification;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.provider.CertificationNumber;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.repository.CertificationRepository;
import com.secondhandplatform.repository.UserRepository;
import com.secondhandplatform.service.user.response.EmailCertificationResponseDto;
import com.secondhandplatform.service.user.response.IdCheckResponse;
import com.secondhandplatform.service.user.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    private final EmailProvider emailProvider;

    public IdCheckResponse checkLoginId(IdCheckRequest request) {
        String loginId = request.getLoginId();

        boolean isDuplicate = userRepository.existsByLoginId(loginId);
        if (isDuplicate) {
            return IdCheckResponse.duplicate();
        }
        return IdCheckResponse.success();
    }

    public EmailCertificationResponseDto emailCertification(EmailCertificationRequest request) {
        String loginId = request.getLoginId();
        String email = request.getEmail();

        boolean isExistId = userRepository.existsByLoginId(loginId);
        if (isExistId) {
            return EmailCertificationResponseDto.duplicateId(email);
        }

        String certificationNumber = CertificationNumber.createCertificationNumber();
        boolean isSuccess = emailProvider.sendMail(email, certificationNumber);
        if (!isSuccess) {
            return EmailCertificationResponseDto.mailSendFail(email);
        }

        Certification certification = Certification.builder()
                .email(email)
                .certificationNumber(certificationNumber)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        certificationRepository.save(certification);
        return EmailCertificationResponseDto.success(email);
    }

    public SignupResponse signup(SignupRequest request) {
        User user = request.toEntity();
        User saveUser = userRepository.save(user);
        return null;
    }
}

