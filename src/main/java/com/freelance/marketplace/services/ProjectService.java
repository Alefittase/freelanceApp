package com.freelance.marketplace.services;

import java.util.List;

import com.freelance.marketplace.entities.*;
import com.freelance.marketplace.dto.ProjectCreationRequest;
import com.freelance.marketplace.dto.ProjectDetailsResponse;
import com.freelance.marketplace.dto.ProjectSummaryResponse;
import com.freelance.marketplace.repos.ProjectRepository;
import com.freelance.marketplace.repos.SkillRepository;
import com.freelance.marketplace.repos.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public Project createProject(ProjectCreationRequest request, String employerEmail) {
        // Get authenticated employer
        Employer employer = (Employer) userRepository.findByEmail(employerEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Create project
        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setStatus(Project.ProjectStatus.ACTIVE);
        project.setEmployer(employer);
        
        // Process skills (auto-create if missing)
        Set<Skill> skills = request.getSkills().stream()
            .map(this::getOrCreateSkill)
            .collect(Collectors.toSet());
        project.setSkills(skills);
        
        return projectRepository.save(project);
    }

    public ProjectDetailsResponse getProjectDetails(UUID projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        
        return ProjectDetailsResponse.builder()
            .id(project.getId())
            .title(project.getTitle())
            .description(project.getDescription())
            .status(project.getStatus().name())
            .employerName(project.getEmployer().getName())
            .skills(project.getSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.toSet()))
            .build();
    }

    public List<ProjectSummaryResponse> getActiveProjects() {
        return projectRepository.findByStatus(Project.ProjectStatus.ACTIVE).stream()
            .map(project -> ProjectSummaryResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .skills(project.getSkills().stream()
                    .map(Skill::getName)
                    .collect(Collectors.toSet()))
                .build())
            .toList();
    }

    public void inactivateProject(UUID projectId, String employerEmail) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        
        // Ownership check
        if (!project.getEmployer().getEmail().equals(employerEmail)) {
            throw new AccessDeniedException("Only project owner can inactivate project");
        }
        
        project.setStatus(Project.ProjectStatus.INACTIVE);
        projectRepository.save(project);
    }

    private Skill getOrCreateSkill(String name) {
        return skillRepository.findByNameIgnoreCase(name)
            .orElseGet(() -> skillRepository.save(new Skill(name)));
    }
}