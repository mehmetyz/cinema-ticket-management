package com.sqlcinema.backend.service;

import com.sqlcinema.backend.model.movie.Genre;
import com.sqlcinema.backend.model.movie.Movie;
import com.sqlcinema.backend.model.movie.MovieComment;
import com.sqlcinema.backend.model.movie.Person;
import com.sqlcinema.backend.model.request.MovieRequest;

import java.util.List;

public interface MovieService {
    List<Genre> getGenres();
    Movie getMovie(int id);
    List<Movie> getMovies(int page, int pageSize, int genreId);
    List<Movie> getPopularMovies(int page, int pageSize, int genreId);
    List<Movie> getNewestMovies(int page, int pageSize, int genreId);

    List<Movie> searchMovies(int page, int size, String query);
    
    Movie randomMovie();
    
    int addMovie(MovieRequest movie);

    int getMovieCount(int genreId);

    int getMovieCount(String query);

    void deleteMovie(int movieId);

    void updateMovie(int movieId, MovieRequest movie);

    List<Person> getCast(int movieId);
    
    List<MovieComment> getComments(int movieId, int page, int size);

    String getKeywords(int movieId);
}
                                                        