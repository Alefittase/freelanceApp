package com.freelance.marketplace.Entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Table
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String password;
    private String email;
    private String phonenumber;
}
