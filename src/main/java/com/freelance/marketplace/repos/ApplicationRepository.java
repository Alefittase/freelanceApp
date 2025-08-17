package com.freelance.marketplace.repos;

import com.freelance.marketplace.Entities.Application;
import com.freelance.marketplace.Entities.Application.ApplicationStatus;
import com.freelance.marketplace.Entities.Freelancer;
import com.freelance.marketplace.Entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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