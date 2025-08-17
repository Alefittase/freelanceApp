package com.freelance.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterFreelancerRequest {
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String name; // Personal name
    
    private String bio; // Optional
}