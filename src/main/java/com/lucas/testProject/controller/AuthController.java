package com.lucas.testProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String home(Model model) {
        return "pages/auth/login";
    }
}
