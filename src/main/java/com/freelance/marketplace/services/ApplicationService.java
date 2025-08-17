package com.freelance.marketplace.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freelance.marketplace.dto.ApplicationResponse;
import com.freelance.marketplace.dto.SubmitApplicationRequest;
import com.freelance.marketplace.entities.Application;
import com.freelance.marketplace.entities.Freelancer;
import com.freelance.marketplace.entities.Project;
import com.freelance.marketplace.repos.ApplicationRepository;
import com.freelance.marketplace.repos.ProjectRepository;
import com.freelance.marketplace.repos.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public Application submitApplication(SubmitApplicationRequest request, String freelancerEmail) {
        // Get project and validate status
        Project project = projectRepository.findById(request.getProjectId())
            .orElseThrow(() -> new RuntimeException("Project not found"));
        
        if (project.getStatus() != Project.ProjectStatus.ACTIVE) {
            throw new RuntimeException("Cannot apply to inactive project");
        }

        // Get authenticated freelancer
        Freelancer freelancer = (Freelancer) userRepository.findByEmail(freelancerEmail)
            .orElseThrow(() -> new RuntimeException("Freelancer not found"));
        
        // Check for duplicate application
        if (applicationRepository.findByProjectAndFreelancer(project, freelancer).isPresent()) {
            throw new RuntimeException("Already applied to this project");
        }

        // Create and save application
        Application application = new Application();
        application.setMessage(request.getMessage());
        application.setStatus(Application.ApplicationStatus.PENDING);
        application.setProject(project);
        application.setFreelancer(freelancer);
        Application savedApp = applicationRepository.save(application);
        
        // Notify project owner
        notificationService.sendEmailNotification(
            project.getEmployer().getEmail(),
            "New Application for " + project.getTitle(),
            "Freelancer " + freelancer.getName() + " applied to your project\nMessage: " + request.getMessage()
        );
        
        return savedApp;
    }

    public List<ApplicationResponse> getApplicationsForProject(UUID projectId, String employerEmail) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        
        // Ownership check
        if (!project.getEmployer().getEmail().equals(employerEmail)) {
            throw new AccessDeniedException("Only project owner can view applications");
        }
        
        return applicationRepository.findByProjectId(projectId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public List<ApplicationResponse> getMyApplications(String freelancerEmail) {
        Freelancer freelancer = (Freelancer) userRepository.findByEmail(freelancerEmail)
            .orElseThrow(() -> new RuntimeException("Freelancer not found"));
        
        return applicationRepository.findByFreelancerId(freelancer.getId()).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void acceptApplication(UUID applicationId, String employerEmail) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        Project project = application.getProject();
        
        // Ownership check
        if (!project.getEmployer().getEmail().equals(employerEmail)) {
            throw new AccessDeniedException("Only project owner can accept applications");
        }
        
        // Reject all other applications
        List<Application> otherApplications = applicationRepository.findByProjectId(project.getId()).stream()
            .filter(app -> !app.getId().equals(applicationId))
            .peek(app -> {
                app.setStatus(Application.ApplicationStatus.REJECTED);
                // Notify rejected freelancer
                notificationService.sendEmailNotification(
                    app.getFreelancer().getEmail(),
                    "Application Rejected: " + project.getTitle(),
                    "Your application was rejected because another freelancer was selected"
                );
            })
            .collect(Collectors.toList());
        applicationRepository.saveAll(otherApplications);
        
        // Accept selected application
        application.setStatus(Application.ApplicationStatus.ACCEPTED);
        applicationRepository.save(application);
        
        // Set project to inactive
        project.setStatus(Project.ProjectStatus.INACTIVE);
        projectRepository.save(project);
        
        // Notify accepted freelancer
        notificationService.sendEmailNotification(
            application.getFreelancer().getEmail(),
            "Application Accepted: " + project.getTitle(),
            "Congratulations! Your application was accepted"
        );
    }

    @Transactional
    public void rejectApplication(UUID applicationId, String employerEmail) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        
        Project project = application.getProject();
        
        // Ownership check
        if (!project.getEmployer().getEmail().equals(employerEmail)) {
            throw new AccessDeniedException("Only project owner can reject applications");
        }
        
        application.setStatus(Application.ApplicationStatus.REJECTED);
        applicationRepository.save(application);
        
        // Notify freelancer
        notificationService.sendEmailNotification(
            application.getFreelancer().getEmail(),
            "Application Rejected: " + project.getTitle(),
            "Your application was rejected by the project owner"
        );
    }

    private ApplicationResponse mapToResponse(Application application) {
        return ApplicationResponse.builder()
            .id(application.getId())
            .message(application.getMessage())
            .status(application.getStatus().name())
            .projectId(application.getProject().getId())
            .projectTitle(application.getProject().getTitle())
            .freelancerId(application.getFreelancer().getId())
            .freelancerName(application.getFreelancer().getName())
            .build();
    }
}