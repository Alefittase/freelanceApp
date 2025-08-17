package com.freelance.marketplace.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String name;
    private String bio;
    private Set<String> skills; // Only used for freelancers
}