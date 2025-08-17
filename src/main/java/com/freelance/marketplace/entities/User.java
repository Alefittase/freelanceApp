package com.freelance.marketplace.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="users", indexes={
    @Index(name="idx_user_type", columnList="user_type")
})
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="user_type", discriminatorType=DiscriminatorType.STRING)
public class User {
    public enum UserType{
        FREELANCER,  // stored as 0
        EMPLOYER     // stored as 1
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable=false, unique=true)
    private String email;
    
    @Column(nullable=false)
    private String password;
    
    @Column(nullable=false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="user_type", insertable=false, updatable=false)
    private UserType userType;
}
