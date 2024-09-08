package com.secondhandplatform.user.controller;

import com.secondhandplatform.dto.user.request.*;
import com.secondhandplatform.dto.user.response.*;
import com.secondhandplatform.user.service.UserService;
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

    }

    @PostMapping("/email-check")
    public ResponseEntity<?> checkEmail(@RequestBody EmailCheckRequestDto request){

    }

    @GetMapping("/email-certification")
    public ResponseEntity<?> sendCertificationEmail(@RequestBody CertificationCodeRequestDto request) {

    }

    @PostMapping("/email-certification")

    }

    @PostMapping("/join")

    }

    @PostMapping("/login")

    }
}
