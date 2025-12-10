package com.starnoh.ecommerce.controllers;

import com.starnoh.ecommerce.dto.AuthResponse;
import com.starnoh.ecommerce.dto.LoginRequest;
import com.starnoh.ecommerce.dto.RegisterRequest;
import com.starnoh.ecommerce.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(LoginRequest request) {
        return authService.login(request);
    }
}
