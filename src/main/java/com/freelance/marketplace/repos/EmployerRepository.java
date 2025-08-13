package com.freelance.marketplace.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelance.marketplace.Entities.Employer;


public interface  EmployerRepository extends JpaRepository<Employer, UUID>{
    //--
}
