package com.example.netflix_server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaRequest {
    
    @NotBlank(message = "Title is required")
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
