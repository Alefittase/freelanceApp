package com.freelance.marketplace.Entities;

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
public class Employer extends User{
    private String companyName;
}
