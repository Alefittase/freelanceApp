package com.freelance.marketplace.dto;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreelancerProfileResponse {
    private UUID id;
    private String email;
    private String name;
    private String bio;
    private Set<String> skills;
}