package com.starnoh.ecommerce.service.impl;

import com.starnoh.ecommerce.dto.AuthResponse;
import com.starnoh.ecommerce.dto.LoginRequest;
import com.starnoh.ecommerce.dto.RegisterRequest;
import com.starnoh.ecommerce.entity.User;
import com.starnoh.ecommerce.entity.UserRoles;
import com.starnoh.ecommerce.repository.UserRepository;
import com.starnoh.ecommerce.security.JwtService;
import com.starnoh.ecommerce.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceImpl implements AuthService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwt;

    public AuthServiceImpl(UserRepository users, PasswordEncoder passwordEncoder, JwtService jwt) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        users.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email already in use");
        });

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole() != null ? request.getRole() : UserRoles.CUSTOMER);

        User saved = users.save(user);
        String token = jwt.generateToken(saved.getId());
        return new AuthResponse(saved.getId(), saved.getEmail(), token);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = users.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwt.generateToken(user.getId());
        return new AuthResponse(user.getId(), user.getEmail(), token);
    }

    @Override
    public boolean validateToken(String token) {
        return jwt.validateToken(token);
    }

    @Override
    public Long extractUserId(String token) {
        return jwt.extractUserId(token);
    }
}
