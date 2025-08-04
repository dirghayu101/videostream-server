package com.example.netflix_server.controller;

import com.example.netflix_server.dto.ApiResponse;
import com.example.netflix_server.dto.LoginRequest;
import com.example.netflix_server.dto.LoginResponse;
import com.example.netflix_server.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    /**
     * Authenticate user login
     * POST /api/v1/auth/login
     * 
     * @param loginRequest user credentials
     * @return ResponseEntity with JWT token and user info
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Login successful", loginResponse));
    }
}
