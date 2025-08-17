package com.freelance.marketplace.services;

import com.freelance.marketplace.entities.Skill;
import com.freelance.marketplace.repos.SkillRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    
    private static final List<String> INITIAL_SKILLS = Arrays.asList(
        "Java", "Spring Boot", "Database Design", "Frontend Development"
    );

    // Preload initial skills on startup
    @PostConstruct
    public void preloadSkills() {
        INITIAL_SKILLS.forEach(skillName -> {
            if (!skillRepository.existsByNameIgnoreCase(skillName)) {
                skillRepository.save(new Skill(skillName));
            }
        });
    }

    // Get all skills
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}