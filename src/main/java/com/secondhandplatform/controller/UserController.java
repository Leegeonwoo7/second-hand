package com.secondhandplatform.controller;

import com.secondhandplatform.dto.user.request.CertificationCodeRequestDto;
import com.secondhandplatform.dto.user.request.EmailCheckRequestDto;
import com.secondhandplatform.dto.user.request.IdCheckRequestDto;
import com.secondhandplatform.dto.user.response.CertificationCodeResponseDto;
import com.secondhandplatform.dto.user.response.EmailCheckResponseDto;
import com.secondhandplatform.dto.user.response.IdCheckResponseDto;
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

    @PostMapping("/email-certification")
    public ResponseEntity<?> sendCertificationEmail(@RequestBody CertificationCodeRequestDto request) {
        CertificationCodeResponseDto response = userService.sendCertificationCode(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        // 데이터베이스 오류가날수도있고.. 실패케이스를 무작정 badRequest로 단정짓고있음
        // 응답에 대한 유연성, 확장성 부족
        return ResponseEntity.badRequest().body(response);
    }


}
