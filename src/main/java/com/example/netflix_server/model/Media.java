package com.example.netflix_server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "media")
public class Media {
    
    @Id
    private String id;
    
    @NotBlank(message = "Title is required")
    @Indexed(background = true)
    private String title;
    
    @NotBlank(message = "Image URL is required")
    private String image;
    
    private String largePoster;
    
    private String synopsis;
    
    @Min(value = 0, message = "Rent price must be positive")
    private Double rentPrice;
    
    @Min(value = 0, message = "Purchase price must be positive")
    private Double purchasePrice;
    
    @Min(value = 1900, message = "Year must be valid")
    private Integer year;
    
    private String genre;
    
    private String description;
    
    @NotNull(message = "Media type is required")
    @Pattern(regexp = "^(movie|tvshow)$", message = "Media type must be 'movie' or 'tvshow'")
    private String mediaType;
    
    private Boolean featured = false;
}
