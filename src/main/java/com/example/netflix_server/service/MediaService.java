package com.example.netflix_server.service;

import com.example.netflix_server.dto.MediaRequest;
import com.example.netflix_server.dto.MediaResponse;
import com.example.netflix_server.model.Media;
import com.example.netflix_server.repository.MediaRepository;
import com.example.netflix_server.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaService {
    
    @Autowired
    private MediaRepository mediaRepository;
    
    /**
     * Create new media (movie/tvshow)
     */
    public MediaResponse createMedia(MediaRequest mediaRequest) {
        Media media = new Media();
        media.setTitle(mediaRequest.getTitle());
        media.setImage(mediaRequest.getImage());
        media.setLargePoster(mediaRequest.getLargePoster());
        media.setSynopsis(mediaRequest.getSynopsis());
        media.setRentPrice(mediaRequest.getRentPrice());
        media.setPurchasePrice(mediaRequest.getPurchasePrice());
        media.setYear(mediaRequest.getYear());
        media.setGenre(mediaRequest.getGenre());
        media.setDescription(mediaRequest.getDescription());
        media.setMediaType(mediaRequest.getMediaType());
        media.setFeatured(mediaRequest.getFeatured() != null ? mediaRequest.getFeatured() : false);
        
        Media savedMedia = mediaRepository.save(media);
        return convertToResponse(savedMedia);
    }
    
    /**
     * Get all movies
     */
    public List<MediaResponse> getAllMovies() {
        List<Media> movies = mediaRepository.findByMediaType("movie");
        return movies.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all TV shows
     */
    public List<MediaResponse> getAllTvShows() {
        List<Media> tvShows = mediaRepository.findByMediaType("tvshow");
        return tvShows.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Search media by title
     */
    public List<MediaResponse> searchMediaByTitle(String title) {
        List<Media> mediaList = mediaRepository.findByTitleContainingIgnoreCase(title);
        return mediaList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get featured movies
     */
    public List<MediaResponse> getFeaturedMovies() {
        List<Media> featuredMovies = mediaRepository.findByMediaTypeAndFeatured("movie", true);
        return featuredMovies.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get featured TV shows
     */
    public List<MediaResponse> getFeaturedTvShows() {
        List<Media> featuredTvShows = mediaRepository.findByMediaTypeAndFeatured("tvshow", true);
        return featuredTvShows.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get media by ID
     */
    public MediaResponse getMediaById(String mediaId) {
        Media media = mediaRepository.findById(mediaId)
            .orElseThrow(() -> new UserNotFoundException("Media with ID " + mediaId + " not found"));
        return convertToResponse(media);
    }
    
    /**
     * Update media
     */
    public MediaResponse updateMedia(String mediaId, MediaRequest mediaRequest) {
        Media existingMedia = mediaRepository.findById(mediaId)
            .orElseThrow(() -> new UserNotFoundException("Media with ID " + mediaId + " not found"));
        
        // Update fields
        existingMedia.setTitle(mediaRequest.getTitle());
        existingMedia.setImage(mediaRequest.getImage());
        existingMedia.setLargePoster(mediaRequest.getLargePoster());
        existingMedia.setSynopsis(mediaRequest.getSynopsis());
        existingMedia.setRentPrice(mediaRequest.getRentPrice());
        existingMedia.setPurchasePrice(mediaRequest.getPurchasePrice());
        existingMedia.setYear(mediaRequest.getYear());
        existingMedia.setGenre(mediaRequest.getGenre());
        existingMedia.setDescription(mediaRequest.getDescription());
        existingMedia.setMediaType(mediaRequest.getMediaType());
        existingMedia.setFeatured(mediaRequest.getFeatured() != null ? mediaRequest.getFeatured() : false);
        
        Media updatedMedia = mediaRepository.save(existingMedia);
        return convertToResponse(updatedMedia);
    }
    
    /**
     * Delete media
     */
    public void deleteMedia(String mediaId) {
        Media media = mediaRepository.findById(mediaId)
            .orElseThrow(() -> new UserNotFoundException("Media with ID " + mediaId + " not found"));
        mediaRepository.delete(media);
    }
    
    /**
     * Convert Media entity to MediaResponse DTO
     */
    private MediaResponse convertToResponse(Media media) {
        return new MediaResponse(
            media.getId(),
            media.getTitle(),
            media.getImage(),
            media.getLargePoster(),
            media.getSynopsis(),
            media.getRentPrice(),
            media.getPurchasePrice(),
            media.getYear(),
            media.getGenre(),
            media.getDescription(),
            media.getMediaType(),
            media.getFeatured()
        );
    }
}
