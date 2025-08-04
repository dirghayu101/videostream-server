package com.example.netflix_server.controller;

import com.example.netflix_server.dto.ApiResponse;
import com.example.netflix_server.dto.MediaRequest;
import com.example.netflix_server.dto.MediaResponse;
import com.example.netflix_server.service.MediaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
@CrossOrigin(origins = "*")
public class MediaController {
    
    @Autowired
    private MediaService mediaService;
    
    /**
     * 1. Create a new movie/tv show
     * POST /api/v1/media
     */
    @PostMapping
    public ResponseEntity<ApiResponse<MediaResponse>> createMedia(
            @Valid @RequestBody MediaRequest mediaRequest) {
        
        MediaResponse mediaResponse = mediaService.createMedia(mediaRequest);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Media created successfully", mediaResponse));
    }
    
    /**
     * 2. Get all movies
     * GET /api/v1/media/movies
     */
    @GetMapping("/movies")
    public ResponseEntity<ApiResponse<List<MediaResponse>>> getAllMovies() {
        List<MediaResponse> movies = mediaService.getAllMovies();
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Movies retrieved successfully", movies));
    }
    
    /**
     * 3. Get all TV shows
     * GET /api/v1/media/tvshows
     */
    @GetMapping("/tvshows")
    public ResponseEntity<ApiResponse<List<MediaResponse>>> getAllTvShows() {
        List<MediaResponse> tvShows = mediaService.getAllTvShows();
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("TV shows retrieved successfully", tvShows));
    }
    
    /**
     * 4. Search media by title
     * GET /api/v1/media/search?title={title}
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MediaResponse>>> searchMediaByTitle(
            @RequestParam String title) {
        
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Title parameter is required"));
        }
        
        List<MediaResponse> mediaList = mediaService.searchMediaByTitle(title);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Search results retrieved successfully", mediaList));
    }
    
    /**
     * 5. Get featured movies
     * GET /api/v1/media/movies/featured?featured=true
     */
    @GetMapping("/movies/featured")
    public ResponseEntity<ApiResponse<List<MediaResponse>>> getFeaturedMovies(
            @RequestParam(required = false, defaultValue = "true") Boolean featured) {
        
        if (!featured) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Featured parameter must be true"));
        }
        
        List<MediaResponse> featuredMovies = mediaService.getFeaturedMovies();
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Featured movies retrieved successfully", featuredMovies));
    }
    
    /**
     * 6. Get featured TV shows
     * GET /api/v1/media/tvshows/featured?featured=true
     */
    @GetMapping("/tvshows/featured")
    public ResponseEntity<ApiResponse<List<MediaResponse>>> getFeaturedTvShows(
            @RequestParam(required = false, defaultValue = "true") Boolean featured) {
        
        if (!featured) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Featured parameter must be true"));
        }
        
        List<MediaResponse> featuredTvShows = mediaService.getFeaturedTvShows();
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Featured TV shows retrieved successfully", featuredTvShows));
    }
    
    /**
     * 7. Get specific media by ID
     * GET /api/v1/media/{mediaId}
     */
    @GetMapping("/{mediaId}")
    public ResponseEntity<ApiResponse<MediaResponse>> getMediaById(@PathVariable String mediaId) {
        
        // Validate mediaId is not empty/null
        if (mediaId == null || mediaId.trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Media ID cannot be empty"));
        }
        
        // Additional validation for MongoDB ObjectId format (24 character hex string)
        if (!isValidObjectId(mediaId)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Invalid media ID format"));
        }
        
        MediaResponse mediaResponse = mediaService.getMediaById(mediaId);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Media retrieved successfully", mediaResponse));
    }
    
    /**
     * 8. Update existing media
     * PUT /api/v1/media/{mediaId}
     */
    @PutMapping("/{mediaId}")
    public ResponseEntity<ApiResponse<MediaResponse>> updateMedia(
            @PathVariable String mediaId,
            @Valid @RequestBody MediaRequest mediaRequest) {
        
        // Validate mediaId is not empty/null
        if (mediaId == null || mediaId.trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Media ID cannot be empty"));
        }
        
        // Additional validation for MongoDB ObjectId format
        if (!isValidObjectId(mediaId)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Invalid media ID format"));
        }
        
        MediaResponse updatedMedia = mediaService.updateMedia(mediaId, mediaRequest);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Media updated successfully", updatedMedia));
    }
    
    /**
     * 9. Delete media
     * DELETE /api/v1/media/{mediaId}
     */
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<ApiResponse<Object>> deleteMedia(@PathVariable String mediaId) {
        
        // Validate mediaId is not empty/null
        if (mediaId == null || mediaId.trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Media ID cannot be empty"));
        }
        
        // Additional validation for MongoDB ObjectId format
        if (!isValidObjectId(mediaId)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Invalid media ID format"));
        }
        
        mediaService.deleteMedia(mediaId);
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Media deleted successfully"));
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
