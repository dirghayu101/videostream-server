package com.example.netflix_server.repository;

import com.example.netflix_server.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
    
    /**
     * Find all movies (mediaType = "movie")
     */
    List<Media> findByMediaType(String mediaType);
    
    /**
     * Find featured movies/tvshows
     */
    List<Media> findByMediaTypeAndFeatured(String mediaType, Boolean featured);
    
    /**
     * Find media by title containing search term (case insensitive)
     */
    @Query("{'title': {$regex: ?0, $options: 'i'}}")
    List<Media> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Find media by title containing search term and media type (case insensitive)
     */
    @Query("{'title': {$regex: ?0, $options: 'i'}, 'mediaType': ?1}")
    List<Media> findByTitleContainingIgnoreCaseAndMediaType(String title, String mediaType);
    
    /**
     * Check if media exists by title and media type
     */
    boolean existsByTitleAndMediaType(String title, String mediaType);
}
