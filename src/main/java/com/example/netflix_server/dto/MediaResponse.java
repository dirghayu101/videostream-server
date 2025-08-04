package com.example.netflix_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponse {
    private String id;
    private String title;
    private String image;
    private String largePoster;
    private String synopsis;
    private Double rentPrice;
    private Double purchasePrice;
    private Integer year;
    private String genre;
    private String description;
    private String mediaType;
    private Boolean featured;
}
