package com.starnoh.ecommerce.service;

import com.starnoh.ecommerce.dto.AuthResponse;
import com.starnoh.ecommerce.dto.LoginRequest;
import com.starnoh.ecommerce.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    boolean validateToken(String token);
    Long extractUserId(String token);
}
