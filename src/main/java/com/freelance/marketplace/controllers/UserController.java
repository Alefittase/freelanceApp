package com.freelance.marketplace.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.marketplace.dto.UserUpdateRequest;
import com.freelance.marketplace.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserProfile(@PathVariable UUID id) {
        Object profile = userService.getUserProfile(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserProfile(
            @PathVariable UUID id,
            @RequestBody UserUpdateRequest request,
            Authentication authentication) {
        userService.updateUserProfile(id, request, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}