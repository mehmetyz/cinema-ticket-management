package com.sqlcinema.backend.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Movie {
    private int movieId;
    private String title;
    private String overview;
    private int runtime;
    private Date releaseDate;
    private float rating;
    private String backdropPath;
    private String posterPath;
    private String trailerLink;
    private String country;
    private String language;
    
    private List<Genre> genres;
}
