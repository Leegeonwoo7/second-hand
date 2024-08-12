package com.secondhandplatform.api.controller;

import com.secondhandplatform.api.request.user.EmailCertificationRequest;
import com.secondhandplatform.api.request.user.IdCheckRequest;
import com.secondhandplatform.service.user.UserService;
import com.secondhandplatform.service.user.response.EmailCertificationResponseDto;
import com.secondhandplatform.service.user.response.IdCheckResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/id-check")
    public ResponseEntity<IdCheckResponse> checkLoginId(@Valid @RequestBody IdCheckRequest request) {
        IdCheckResponse responseBody = userService.checkLoginId(request);

        log.info("controller - isDuplicate(): {}", responseBody.isDuplicate());

        if (responseBody.isDuplicate()) {
            log.info("controller duplicate: {}", responseBody.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(responseBody);
        }

        log.info("controller success: {}", responseBody.getMessage());
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/users/email-certification")
    public ResponseEntity<EmailCertificationResponseDto> certificationEmail(@Valid @RequestBody EmailCertificationRequest request) {
        EmailCertificationResponseDto responseBody = userService.emailCertification(request);

        if (responseBody.isDuplicateId()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(responseBody);
        }

        if (responseBody.isCertificationFail()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseBody);
        }

        return ResponseEntity.ok()
                .body(responseBody);
    }
}
