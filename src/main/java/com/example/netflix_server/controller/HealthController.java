package com.example.netflix_server.controller;

import com.example.netflix_server.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
@CrossOrigin(origins = "*")
public class HealthController {
    
    /**
     * Simple health check endpoint
     * GET /api/v1/health
     * 
     * @return ResponseEntity with health status
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("status", "UP");
        healthData.put("timestamp", LocalDateTime.now());
        healthData.put("service", "Netflix Server API");
        healthData.put("version", "1.0.0");
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Health check successful", healthData));
    }
    
    /**
     * Simple ping endpoint
     * GET /api/v1/health/ping
     * 
     * @return Simple pong response
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
    
    /**
     * Server info endpoint
     * GET /api/v1/health/info
     * 
     * @return Server information
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        Map<String, String> info = new HashMap<>();
        info.put("application", "Netflix Server");
        info.put("description", "Server side for the netflix clone project");
        info.put("java.version", System.getProperty("java.version"));
        info.put("os.name", System.getProperty("os.name"));
        
        return ResponseEntity.ok(info);
    }
}
