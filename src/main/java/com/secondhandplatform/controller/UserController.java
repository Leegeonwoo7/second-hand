package com.secondhandplatform.controller;

import com.secondhandplatform.dto.user.request.*;
import com.secondhandplatform.dto.user.response.*;
import com.secondhandplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/id-check")
    public ResponseEntity<?> checkLoginId(@RequestBody IdCheckRequestDto request) {
        String loginId = request.getLoginId();

        IdCheckResponseDto idCheckResponseDto = userService.checkLoginIdAvailability(loginId);
        log.debug("[Controller] - 중복여부: {}", idCheckResponseDto.getMessage());
        boolean isDuplicate = idCheckResponseDto.isDuplicate();

        if (isDuplicate) {
            return ResponseEntity.badRequest()
                    .body(idCheckResponseDto);
        }

        return ResponseEntity.ok(idCheckResponseDto);
    }

    @PostMapping("/email-check")
    public ResponseEntity<?> checkEmail(@RequestBody EmailCheckRequestDto request){
        String email = request.getEmail();
        EmailCheckResponseDto response = userService.checkEmailAvailability(email);
        boolean isExist = response.isExist();

        if (isExist) {
            return ResponseEntity.badRequest()
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/email-certification")
    public ResponseEntity<?> sendCertificationEmail(@RequestBody CertificationCodeRequestDto request) {
        CertificationCodeResponseDto response = userService.sendCertificationCode(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        // 데이터베이스 오류가날수도있고.. 실패케이스를 무작정 badRequest로 단정짓고있음
        // 응답에 대한 유연성, 확장성 부족
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/email-certification")
    public ResponseEntity<?> certificationCodeCheck(@RequestBody CertificationCheckRequestDto request) {

        CertificationCheckResponseDto response = userService.certificationCheck(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest()
                .body(response);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody CreateUserRequestDto request) {
        CreateUserResponseDto response = userService.join(request);

        if (response.getId() == null) {
            return ResponseEntity.badRequest()
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response;

        try {
            response = userService.login(request);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(null);
        }

        return ResponseEntity.ok(response);
    }
}
