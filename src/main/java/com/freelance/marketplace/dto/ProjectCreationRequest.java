package com.freelance.marketplace.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectCreationRequest {
    @NotBlank
    @Size(max = 100)
    private String title;
    
    @NotBlank
    @Size(max = 1000)
    private String description;
    
    private Set<String> skills;
}