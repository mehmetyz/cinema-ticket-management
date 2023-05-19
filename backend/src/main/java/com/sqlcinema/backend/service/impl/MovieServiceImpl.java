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
    public Movie getMovie(int id) {
        return movieRepository.getMovieById(id);
    }

    @Override
    public List<Movie> getMovies(int page, int pageSize, int genreId) {
        return movieRepository.getMovies(page, pageSize, genreId, MovieOrder.ID);
    }

    @Override
    public List<Movie> getPopularMovies(int page, int pageSize, int genreId) {
        return movieRepository.getMovies(page, pageSize, genreId, MovieOrder.POPULARITY.descending());
    }

    @Override
    public List<Movie> getNewestMovies(int page, int pageSize, int genreId) {
        return movieRepository.getMovies(page, pageSize, genreId, MovieOrder.RELEASE_DATE.descending());
    }

    @Override
    public List<Movie> searchMovies(int page, int size, String query) {
        return movieRepository.searchMovies(page, size, query);
    }

    @Override
    public Movie randomMovie() {
        return movieRepository.randomMovie();
    }
    
    @Override
    public void addMovie(Movie movie) {
        movieRepository.addMovie(movie);
    }

    @Override
    public int getMovieCount(int genreId) {
        return movieRepository.getMovieCount(genreId);
    }

    @Override
    public int getMovieCount(String query) {
        return movieRepository.getMovieCountByQuery(query);
    }
}
