package com.freelance.marketplace.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitApplicationRequest {
    @NotNull
    private UUID projectId;
    
    @NotBlank
    private String message;
}