package com.codepuppeteer.sistema_gastos_clientes.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

    @GetMapping("/auth/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/auth/register")
    public String registerPage() {
        return "auth/register";
    }

    @GetMapping("/auth/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    @GetMapping("/auth/reset-password")
    public String resetPasswordPage() {
        return "auth/reset-password";
    }
}
