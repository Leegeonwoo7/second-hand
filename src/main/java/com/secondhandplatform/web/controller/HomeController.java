package com.secondhandplatform.web.controller;

import com.secondhandplatform.domain.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String goHome() {
        return "/home";
    }

}
