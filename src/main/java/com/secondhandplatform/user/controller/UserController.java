package com.secondhandplatform.user.controller;

import com.secondhandplatform.user.domain.UserRepository;
import com.secondhandplatform.user.dto.request.*;
import com.secondhandplatform.user.dto.response.JoinResponse;
import com.secondhandplatform.user.dto.response.LoginResponse;
import com.secondhandplatform.user.dto.response.Response;
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
    private final UserRepository userRepository;

    @PostMapping("/id-check")
    public ResponseEntity<?> checkLoginId(@RequestBody UsernameCheckRequest request) {
        String username = request.getUsername();

        Response response = userService.checkLoginIdAvailability(username);
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/email-check")
    public ResponseEntity<?> checkEmail(@RequestBody CheckEmailRequest request){
        String email = request.getEmail();

        Response response = userService.checkEmailAvailability(email);
        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/email-certification")
    public ResponseEntity<?> sendCertificationEmail(@RequestBody CertificationCodeRequest request) {
        Response response = userService.sendCertificationCode(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/email-certification")
    public ResponseEntity<?> checkCertificationCode(@RequestBody CertificationCodeCheckRequest request) {
        Response response = userService.certificationCheck(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequest request) {
        JoinResponse response = userService.join(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(response);
    }

}
