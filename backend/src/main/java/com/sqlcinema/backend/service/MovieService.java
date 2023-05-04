package com.sqlcinema.backend.service;

import com.sqlcinema.backend.model.Genre;
import com.sqlcinema.backend.model.Movie;

import java.util.List;

public interface MovieService {
    List<Genre> getGenres();
    List<Movie> getMovies(int page, int pageSize);
    List<Movie> getPopularMovies(int page, int pageSize);
    List<Movie> getNewestMovies(int page, int pageSize);
    
    Movie randomMovie();
}
