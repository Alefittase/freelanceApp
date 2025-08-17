package com.freelance.marketplace.repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelance.marketplace.entities.Employer;
import com.freelance.marketplace.entities.Project;
import com.freelance.marketplace.entities.Project.ProjectStatus;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>{
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByState(ProjectStatus state);
    List<Project> findByEmployer(Employer employer);
}
