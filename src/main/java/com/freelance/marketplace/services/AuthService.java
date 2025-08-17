package com.freelance.marketplace.services;

import com.freelance.marketplace.entities.Employer;
import com.freelance.marketplace.entities.Freelancer;
import com.freelance.marketplace.entities.User;
import com.freelance.marketplace.dto.AuthResponse;
import com.freelance.marketplace.dto.LoginRequest;
import com.freelance.marketplace.dto.RegisterEmployerRequest;
import com.freelance.marketplace.dto.RegisterFreelancerRequest;
import com.freelance.marketplace.repos.EmployerRepository;
import com.freelance.marketplace.repos.FreelancerRepository;
import com.freelance.marketplace.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final FreelancerRepository freelancerRepository;
    private final EmployerRepository employerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthService jwtAuthService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registerFreelancer(RegisterFreelancerRequest request) {
        // Validate email uniqueness
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Create and save freelancer
        Freelancer freelancer = new Freelancer();
        freelancer.setEmail(request.getEmail());
        freelancer.setPassword(passwordEncoder.encode(request.getPassword()));
        freelancer.setName(request.getName());
        freelancer.setBio(request.getBio());
        freelancerRepository.save(freelancer);

        // Generate JWT
        String token = jwtAuthService.generateToken(freelancer);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse registerEmployer(RegisterEmployerRequest request) {
        // Validate email uniqueness
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Create and save employer
        Employer employer = new Employer();
        employer.setEmail(request.getEmail());
        employer.setPassword(passwordEncoder.encode(request.getPassword()));
        employer.setName(request.getCompanyName());
        employer.setBio(request.getBio());
        employerRepository.save(employer);

        // Generate JWT
        String token = jwtAuthService.generateToken(employer);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse login(LoginRequest request) {
        // Authenticate credentials
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // Load user and generate token
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = jwtAuthService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }
}