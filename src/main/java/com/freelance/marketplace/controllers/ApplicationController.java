package com.freelance.marketplace.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.marketplace.dto.ApplicationResponse;
import com.freelance.marketplace.dto.SubmitApplicationRequest;
import com.freelance.marketplace.entities.Application;
import com.freelance.marketplace.services.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/projects/{projectId}/applications")
    public ResponseEntity<Application> submitApplication(
            @PathVariable UUID projectId,
            @Valid @RequestBody SubmitApplicationRequest request,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            applicationService.submitApplication(request, authentication.getName())
        );
    }

    @GetMapping("/projects/{projectId}/applications")
    public ResponseEntity<List<ApplicationResponse>> getProjectApplications(
            @PathVariable UUID projectId,
            Authentication authentication) {
        return ResponseEntity.ok(
            applicationService.getApplicationsForProject(projectId, authentication.getName())
        );
    }

    @GetMapping("/applications/me")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(Authentication authentication) {
        return ResponseEntity.ok(
            applicationService.getMyApplications(authentication.getName())
        );
    }

    @PutMapping("/applications/{id}/accept")
    public ResponseEntity<Void> acceptApplication(
            @PathVariable UUID id,
            Authentication authentication) {
        applicationService.acceptApplication(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/applications/{id}/reject")
    public ResponseEntity<Void> rejectApplication(
            @PathVariable UUID id,
            Authentication authentication) {
        applicationService.rejectApplication(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}