package com.secondhandplatform.service.user;

import com.secondhandplatform.api.request.user.DuplicateLoginIdRequest;
import com.secondhandplatform.api.request.user.EmailCertificationRequest;
import com.secondhandplatform.api.request.user.SendCertificationRequest;
import com.secondhandplatform.provider.CertificationNumber;
import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.repository.CertificationRepository;
import com.secondhandplatform.repository.UserRepository;
import com.secondhandplatform.service.user.response.DuplicateLoginIdResponse;
import com.secondhandplatform.service.user.response.EmailCertificationResponse;
import com.secondhandplatform.service.user.response.SendCertificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailProvider emailProvider;
    private final CertificationRepository certificationRepository;

    public DuplicateLoginIdResponse validateLoginId(DuplicateLoginIdRequest request) {
        String loginId = request.getLoginId();
        boolean isDuplicate = userRepository.existsByLoginId(loginId);

        if (isDuplicate) {
            return DuplicateLoginIdResponse.isDuplicate();
        }

        return DuplicateLoginIdResponse.isNotDuplicate();
    }

    public SendCertificationResponse sendCertification(SendCertificationRequest request) {
        String email = request.getEmail();
        String certificationNumber = CertificationNumber.createCertificationNumber();

        try {
            emailProvider.sendMail(email, certificationNumber);

        } catch (Exception e) {
            e.printStackTrace();
            log.warn("외부 이메일 전송 에러");
            return SendCertificationResponse.fail();
        }



        return SendCertificationResponse.success();
    }
}
