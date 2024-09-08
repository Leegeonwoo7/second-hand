package com.secondhandplatform.user.controller;

import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import com.secondhandplatform.user.dto.request.UsernameCheckRequest;
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

//    @PostMapping("/email-check")
//    public ResponseEntity<?> checkEmail(@RequestBody EmailCheckRequestDto request){
//
//    }
//
//    @GetMapping("/email-certification")
//    public ResponseEntity<?> sendCertificationEmail(@RequestBody CertificationCodeRequestDto request) {
//
//    }
//
//    @PostMapping("/email-certification")
//
//    }
//
//    @PostMapping("/join")
//
//    }
//
//    @PostMapping("/login")
//
//    }
}
