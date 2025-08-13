package com.freelance.marketplace.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelance.marketplace.Entities.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID>{
    //--
}
