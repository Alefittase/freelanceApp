package com.freelance.marketplace.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.freelance.marketplace.dto.ProjectCreationRequest;
import com.freelance.marketplace.dto.ProjectDetailsResponse;
import com.freelance.marketplace.dto.ProjectSummaryResponse;
import com.freelance.marketplace.entities.Project;
import com.freelance.marketplace.entities.Skill;
import com.freelance.marketplace.services.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDetailsResponse> createProject(
            @Valid @RequestBody ProjectCreationRequest request,
            Authentication authentication) {
        Project project = projectService.createProject(request, authentication.getName());
        ProjectDetailsResponse response = ProjectDetailsResponse.builder()
            .id(project.getId())
            .title(project.getTitle())
            .description(project.getDescription())
            .status(project.getStatus().name())
            .employerName(project.getEmployer().getName())
            .skills(project.getSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.toSet()))
            .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> getActiveProjects() {
        return ResponseEntity.ok(projectService.getActiveProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailsResponse> getProjectDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectDetails(id));
    }

    @PutMapping("/{id}/inactivate")
    public ResponseEntity<Void> inactivateProject(
            @PathVariable UUID id,
            Authentication authentication) {
        projectService.inactivateProject(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}