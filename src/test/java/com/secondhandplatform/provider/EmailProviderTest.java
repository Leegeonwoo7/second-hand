package com.secondhandplatform.provider;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class EmailProviderTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailProvider emailProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("이메일 전송에 성공한다.")
    void sendMailSuccess() {
        // given
        String email = "test@example.com";
        String certificationNumber = "1234";

        MimeMessage message = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(message);

        //when
        boolean result = emailProvider.sendMail(email, certificationNumber);

        //then
        assertTrue(result);
        verify(javaMailSender, times(1)).send(message);
    }

    @Test
    @Order(2)
    @DisplayName("이메일 전송에 실패한다.")
    void sendMailFail() {
        // given
        String email = "test@example.com";
        String certificationNumber = "1234";

        //when
        boolean result = emailProvider.sendMail(email, certificationNumber);

        //then
        assertFalse(result);
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }
}