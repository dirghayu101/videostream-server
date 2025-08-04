package com.example.netflix_server.service;

import com.example.netflix_server.dto.LoginRequest;
import com.example.netflix_server.dto.LoginResponse;
import com.example.netflix_server.dto.UserResponse;
import com.example.netflix_server.model.User;
import com.example.netflix_server.repository.UserRepository;
import com.example.netflix_server.util.JwtUtil;
import com.example.netflix_server.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Authenticate user with email and password
     * @param loginRequest login credentials
     * @return LoginResponse with JWT token and user info
     * @throws UserNotFoundException if authentication fails
     */
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));
        
        // Check password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email or password");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateJwtToken(user.getEmail(), user.getId());
        
        // Create user response (without password)
        UserResponse userResponse = new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail()
        );
        
        return new LoginResponse(token, userResponse);
    }
    
    /**
     * Validate if the authenticated user can access the requested user data
     * @param authenticatedEmail email from JWT token
     * @param requestedUserId ID of user data being requested
     * @return true if access is allowed
     */
    public boolean canAccessUserData(String authenticatedEmail, String requestedUserId) {
        User authenticatedUser = userRepository.findByEmail(authenticatedEmail)
            .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));
        
        // User can only access their own data
        return authenticatedUser.getId().equals(requestedUserId);
    }
    
    /**
     * Validate if the authenticated user can access the requested user data by email
     * @param authenticatedEmail email from JWT token
     * @param requestedEmail email of user data being requested
     * @return true if access is allowed
     */
    public boolean canAccessUserDataByEmail(String authenticatedEmail, String requestedEmail) {
        // User can only access their own data
        return authenticatedEmail.equals(requestedEmail);
    }
}
