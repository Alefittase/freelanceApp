package com.freelance.marketplace.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationResponse {
    private UUID id;
    private String message;
    private String status;
    private UUID projectId;
    private String projectTitle;
    private UUID freelancerId;
    private String freelancerName;
}