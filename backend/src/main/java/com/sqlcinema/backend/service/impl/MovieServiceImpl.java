package com.sqlcinema.backend.service.impl;

import com.sqlcinema.backend.model.Genre;
import com.sqlcinema.backend.model.Movie;
import com.sqlcinema.backend.model.order.MovieOrder;
import com.sqlcinema.backend.repository.MovieRepository;
import com.sqlcinema.backend.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    
    private final MovieRepository movieRepository;
    
    @Override
    public List<Genre> getGenres() {
        return movieRepository.getGenres();
    }

    @Override
    public List<Movie> getMovies(int page, int pageSize) {
        return movieRepository.getMovies(page, pageSize, MovieOrder.ID);
    }

    @Override
    public List<Movie> getPopularMovies(int page, int pageSize) {
        return movieRepository.getMovies(page, pageSize, MovieOrder.POPULARITY.descending());
    }

    @Override
    public List<Movie> getNewestMovies(int page, int pageSize) {
        return movieRepository.getMovies(page, pageSize, MovieOrder.RELEASE_DATE.descending());
    }

    @Override
    public Movie randomMovie() {
        return movieRepository.randomMovie();
    }
}
