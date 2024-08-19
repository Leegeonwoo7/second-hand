package com.secondhandplatform.web.controller.user;

import com.secondhandplatform.api.request.user.DuplicateLoginIdRequest;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.service.user.UserService;
import com.secondhandplatform.service.user.response.DuplicateLoginIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/users/sign-up")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "/user/newForm";
    }

    @GetMapping("/users/login")
    public String loginForm() {
        return "/user/loginForm";
    }

//    @PostMapping("/users/check-id")
//    public String checkDuplicateId(DuplicateLoginIdRequest request) {
//        DuplicateLoginIdResponse response = userService.checkDuplicateId(request);
//
//        if (response.isDuplicate()) {
//
//            return "/user/newForm";
//        }
//
//        return null;
//    }
}
