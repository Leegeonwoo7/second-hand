package com.secondhandplatform.user.controller;

import com.secondhandplatform.user.domain.UserRepository;
import com.secondhandplatform.user.dto.request.*;
import com.secondhandplatform.user.dto.response.JoinResponse;
import com.secondhandplatform.user.dto.response.LoginResponse;
import com.secondhandplatform.user.dto.response.UserResponse;
import com.secondhandplatform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/id-check")
    public ResponseEntity<?> checkLoginId(@RequestBody UsernameCheckRequest request) {
        String username = request.getUsername();

        UserResponse response = userService.checkLoginIdAvailability(username);
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/email-check")
    public ResponseEntity<?> checkEmail(@RequestBody CheckEmailRequest request){
        String email = request.getEmail();

        UserResponse response = userService.checkEmailAvailability(email);
        return ResponseEntity.ok()
                .body(response);
    }

    //이메일 발송
    @PostMapping("/email-certification")
    public ResponseEntity<?> sendCertificationEmail(@RequestBody CertificationCodeRequest request) {
        log.info("POST - /users/email-certification");
        UserResponse response = userService.sendCertificationCode(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/code-check")
    public ResponseEntity<?> checkCertificationCode(@RequestBody CertificationCodeCheckRequest request) {
        log.info("POST - /users/code-check");
        UserResponse response = userService.certificationCheck(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequest request) {
        JoinResponse response = userService.join(request);
        log.debug("POST /users/join");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("/users/login"));

        return ResponseEntity.status(200)
//                .headers(headers)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("POST - /users/login");
        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(response);
    }



}
