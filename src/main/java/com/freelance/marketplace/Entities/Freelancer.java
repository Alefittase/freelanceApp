package com.freelance.marketplace.Entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Description;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Table
@Entity
@DiscriminatorValue("0")
public class Freelancer extends User{
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "freelancer_skills",
        joinColumns = @JoinColumn(name="freelancer_id"),
        inverseJoinColumns = @JoinColumn(name="skill_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Skill> skills = new HashSet<>();
}