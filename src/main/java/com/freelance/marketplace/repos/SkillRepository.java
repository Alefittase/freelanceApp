package com.freelance.marketplace.repos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelance.marketplace.entities.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {
    boolean existsByNameIgnoreCase(String skillName);
    Optional<Skill> findByNameIgnoreCase(String name);
}