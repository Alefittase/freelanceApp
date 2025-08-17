package com.freelance.marketplace.repos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelance.marketplace.entities.Freelancer;
@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, UUID>{
    @EntityGraph(attributePaths = {"skills"})
    Optional<Freelancer> findWithSkillsById(UUID id);
}
