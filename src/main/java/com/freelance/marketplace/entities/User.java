package com.freelance.marketplace.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_type", columnList = "user_type")
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails {
    public enum UserType {
        FREELANCER,
        EMPLOYER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_type", insertable = false, updatable = false)
    private UserType userType;

    // ======== UserDetails Methods ========
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + userType.name();
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email; // Use email as username for authentication
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // No account expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // No locking
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // No credential expiration
    }

    @Override
    public boolean isEnabled() {
        return true; // Always enabled
    }
}