package com.freelance.marketplace.repos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelance.marketplace.Entities.Employer;

@Repository
public interface  EmployerRepository extends JpaRepository<Employer, UUID>{
    @EntityGraph(attributePaths = {"projects"})
    Optional<Employer> findWithProjectsById(UUID id);
}
