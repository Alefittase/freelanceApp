package com.freelance.marketplace.services;

import com.freelance.marketplace.entities.*;
import com.freelance.marketplace.dto.FreelancerProfileResponse;
import com.freelance.marketplace.dto.EmployerProfileResponse;
import com.freelance.marketplace.dto.UserUpdateRequest;
import com.freelance.marketplace.repos.FreelancerRepository;
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
public class UserService {
    private final UserRepository userRepository;
    private final FreelancerRepository freelancerRepository;
    private final SkillRepository skillRepository;

    public Object getUserProfile(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getUserType() == User.UserType.FREELANCER) {
            Freelancer freelancer = freelancerRepository.findWithSkillsById(userId)
                .orElseThrow(() -> new RuntimeException("Freelancer not found"));
            return mapToFreelancerProfile(freelancer);
        } else {
            // For Employer, return employer-specific DTO
            return mapToEmployerProfile((Employer) user);
        }
    }

    public void updateUserProfile(UUID userId, UserUpdateRequest request, String authenticatedEmail) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Ownership check
        if (!user.getEmail().equals(authenticatedEmail)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        // Common fields
        user.setName(request.getName());
        user.setBio(request.getBio());
        userRepository.save(user);

        // Role-specific updates
        if (user.getUserType() == User.UserType.FREELANCER) {
            updateFreelancerSkills(userId, request.getSkills());
        }
        // (Employers have no additional fields beyond common ones)
    }

    private void updateFreelancerSkills(UUID freelancerId, Set<String> skillNames) {
        Freelancer freelancer = freelancerRepository.findWithSkillsById(freelancerId)
            .orElseThrow(() -> new RuntimeException("Freelancer not found"));
        
        Set<Skill> skills = skillNames.stream()
            .map(this::getOrCreateSkill)
            .collect(Collectors.toSet());
        
        freelancer.setSkills(skills);
        freelancerRepository.save(freelancer);
    }

    private Skill getOrCreateSkill(String name) {
        return skillRepository.findByNameIgnoreCase(name)
            .orElseGet(() -> skillRepository.save(new Skill(name)));
    }

    // DTO Mappers
    private FreelancerProfileResponse mapToFreelancerProfile(Freelancer freelancer) {
        return FreelancerProfileResponse.builder()
            .id(freelancer.getId())
            .email(freelancer.getEmail())
            .name(freelancer.getName())
            .bio(freelancer.getBio())
            .skills(freelancer.getSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.toSet()))
            .build();
    }

    private EmployerProfileResponse mapToEmployerProfile(Employer employer) {
        return EmployerProfileResponse.builder()
            .id(employer.getId())
            .email(employer.getEmail())
            .companyName(employer.getName()) // Name stores company name
            .bio(employer.getBio())
            .build();
    }
}