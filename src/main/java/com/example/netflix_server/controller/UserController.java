package com.example.netflix_server.controller;

import com.example.netflix_server.dto.ApiResponse;
import com.example.netflix_server.dto.UserRegistrationRequest;
import com.example.netflix_server.dto.UserResponse;
import com.example.netflix_server.service.UserService;
import com.example.netflix_server.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    // Autowired provides access to UserService methods.
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;
    
    /**
     * Health check endpoint
     * GET /api/v1/users/health
     * 
     * @return ResponseEntity with simple health status
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Netflix Server API is running!", "Server is healthy"));
    }
    
    /**
     * Register a new user
     * POST /api/v1/users/register
     * 
     * @param registrationRequest the user registration data
     * @return ResponseEntity with ApiResponse containing user data
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(
            @Valid @RequestBody UserRegistrationRequest registrationRequest) {
        
        UserResponse userResponse = userService.registerUser(registrationRequest);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("User registered successfully", userResponse));
    }
    
    /**
     * Get a specific user by ID (Protected - requires authentication)
     * GET /api/v1/users/{userId}
     * 
     * @param userId the user ID
     * @return ResponseEntity with ApiResponse containing user data
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String userId) {
        
        // Get authenticated user email from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = authentication.getName();
        
        // Validate userId is not empty/null
        if (userId == null || userId.trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("User ID cannot be empty"));
        }
        
        // Additional validation for MongoDB ObjectId format (24 character hex string)
        if (!isValidObjectId(userId)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Invalid user ID format"));
        }
        
        // Check if authenticated user can access this user data
        if (!authService.canAccessUserData(authenticatedEmail, userId)) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Access denied: You can only access your own data"));
        }
        
        UserResponse userResponse = userService.getUserById(userId);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("User retrieved successfully", userResponse));
    }
    
    /**
     * Get a specific user by email (Protected - requires authentication)
     * GET /api/v1/users/email/{email}
     * 
     * @param email the user email
     * @return ResponseEntity with ApiResponse containing user data
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(@PathVariable String email) {
        
        // Get authenticated user email from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = authentication.getName();
        
        // Validate email is not empty/null
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Email cannot be empty"));
        }
        
        // Check if authenticated user can access this user data
        if (!authService.canAccessUserDataByEmail(authenticatedEmail, email)) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Access denied: You can only access your own data"));
        }
        
        UserResponse userResponse = userService.getUserByEmail(email);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("User retrieved successfully", userResponse));
    }
    
    /**
     * Validate MongoDB ObjectId format
     * @param id the ID to validate
     * @return true if valid ObjectId format, false otherwise
     */
    private boolean isValidObjectId(String id) {
        return id != null && id.matches("^[0-9a-fA-F]{24}$");
    }
}
