package com.freelance.marketplace.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelance.marketplace.entities.Application;
import com.freelance.marketplace.entities.Freelancer;
import com.freelance.marketplace.entities.Project;
import com.freelance.marketplace.entities.Application.ApplicationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByProjectId(UUID projectId);
    List<Application> findByFreelancerId(UUID freelancerId);
    List<Application> findByStatus(ApplicationStatus status);
    Optional<Application> findByProjectAndFreelancer(Project project, Freelancer freelancer);
}