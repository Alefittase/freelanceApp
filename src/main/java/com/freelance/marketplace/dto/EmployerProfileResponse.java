package com.freelance.marketplace.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmployerProfileResponse {
    private UUID id;
    private String email;
    private String companyName;
    private String bio;
}