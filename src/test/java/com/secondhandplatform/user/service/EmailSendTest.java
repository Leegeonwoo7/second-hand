package com.secondhandplatform.user.service;

import com.secondhandplatform.provider.EmailProvider;
import com.secondhandplatform.user.domain.Certification;
import com.secondhandplatform.user.domain.CertificationRepository;
import com.secondhandplatform.user.dto.request.CertificationCodeRequest;
import com.secondhandplatform.user.dto.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSendTest {

    @Mock
    EmailProvider emailProvider;

    @Mock
    CertificationRepository certificationRepository;

    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("이메일 전송에 성공한다.")
    void sendCertificationCode() {
        //given
        String email = "test@example.com";
        String certificationCode = "1234";

        CertificationCodeRequest request = new CertificationCodeRequest(email, certificationCode);

        try (MockedStatic<Certification> mockedStatic = Mockito.mockStatic(Certification.class)) {
            Certification certificationMock = Certification.builder()
                    .email(email)
                    .certificationNumber(certificationCode)
                    .build();
            mockedStatic.when(() -> Certification.create(email, certificationCode)).thenReturn(certificationMock);

            when(emailProvider.sendMail(email, certificationCode)).thenReturn(true);

            //when
            Response response = userService.sendCertificationCode(request);

            //then
            assertThat(response.getMessage()).isEqualTo(Response.EMAIL_SEND_OK);
        }
    }
}
