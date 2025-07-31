package com.example.netflix_server.service;

import com.example.netflix_server.dto.UserRegistrationRequest;
import com.example.netflix_server.dto.UserResponse;
import com.example.netflix_server.model.User;
import com.example.netflix_server.repository.UserRepository;
import com.example.netflix_server.exception.UserAlreadyExistsException;
import com.example.netflix_server.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    /**
     * Register a new user
     * @param registrationRequest the user registration data
     * @return UserResponse containing user details (without password)
     * @throws UserAlreadyExistsException if email already exists
     */
    public UserResponse registerUser(UserRegistrationRequest registrationRequest) {
        // Check if user already exists
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + registrationRequest.getEmail() + " already exists");
        }
        
        // Create new user
        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        
        // Save user to database
        User savedUser = userRepository.save(user);
        
        // Return user response (without password)
        return new UserResponse(
            savedUser.getId(),
            savedUser.getFirstName(),
            savedUser.getLastName(),
            savedUser.getEmail()
        );
    }
    
    /**
     * Get user by ID
     * @param userId the user ID
     * @return UserResponse containing user details (without password)
     * @throws UserNotFoundException if user not found
     */
    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        
        return new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail()
        );
    }
    
    /**
     * Get user by email
     * @param email the user email
     * @return UserResponse containing user details (without password)
     * @throws UserNotFoundException if user not found
     */
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        
        return new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail()
        );
    }
}
