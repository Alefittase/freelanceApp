package com.freelance.marketplace.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelance.marketplace.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findByUserType(User.UserType userType);
}
