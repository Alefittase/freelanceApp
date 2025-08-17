package com.freelance.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterEmployerRequest {
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String companyName; // Stored as name in User
    
    private String bio; // Optional
}