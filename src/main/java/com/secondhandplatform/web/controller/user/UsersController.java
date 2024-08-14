package com.secondhandplatform.web.controller.user;

import com.secondhandplatform.api.request.user.LoginRequest;
import com.secondhandplatform.api.request.user.SignUpRequest;
import com.secondhandplatform.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "/user/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginRequest request) {

        return "/user/loginForm";
    }

    @GetMapping("/sign-up")
    public String signUpForm() {
        return "/user/newForm";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid SignUpRequest request) {
        userService.signUp(request);
        return "redirect:/users/login";
    }
}
