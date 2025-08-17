package com.freelance.marketplace.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.marketplace.dto.AuthResponse;
import com.freelance.marketplace.dto.LoginRequest;
import com.freelance.marketplace.dto.RegisterEmployerRequest;
import com.freelance.marketplace.dto.RegisterFreelancerRequest;
import com.freelance.marketplace.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/freelancer")
    public ResponseEntity<AuthResponse> registerFreelancer(
            @Valid @RequestBody RegisterFreelancerRequest request) {
        AuthResponse response = authService.registerFreelancer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register/employer")
    public ResponseEntity<AuthResponse> registerEmployer(
            @Valid @RequestBody RegisterEmployerRequest request) {
        AuthResponse response = authService.registerEmployer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}