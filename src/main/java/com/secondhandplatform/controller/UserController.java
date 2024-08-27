package com.secondhandplatform.controller;

import com.secondhandplatform.dto.user.request.IdCheckRequestDto;
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

        IdCheckResponseDto idCheckResponseDto = userService.checkLoginIdAvailability(request);
        log.debug("[Controller] - 중복여부: {}", idCheckResponseDto.getMessage());
        boolean isDuplicate = idCheckResponseDto.isDuplicate();

        if (isDuplicate) {
            return ResponseEntity.badRequest()
                    .body(idCheckResponseDto);
        }

        return ResponseEntity.ok(idCheckResponseDto);
    }
}
