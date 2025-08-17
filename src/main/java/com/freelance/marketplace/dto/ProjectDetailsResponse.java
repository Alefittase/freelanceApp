package com.freelance.marketplace.dto;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDetailsResponse {
    private UUID id;
    private String title;
    private String description;
    private String status;
    private String employerName;
    private Set<String> skills;
}