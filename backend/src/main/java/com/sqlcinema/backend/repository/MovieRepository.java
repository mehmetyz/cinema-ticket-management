package com.sqlcinema.backend.repository;

import com.sqlcinema.backend.common.CustomLogger;
import com.sqlcinema.backend.model.Genre;
import com.sqlcinema.backend.model.Movie;
import com.sqlcinema.backend.model.order.MovieOrder;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sqlcinema.backend.common.Constants.createObjectArray;
import static com.sqlcinema.backend.common.Constants.createStringArray;

@Repository
@AllArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomLogger logger;
    
    public List<Genre> getGenres() {
        String query = "SELECT * FROM Genre";

        logger.sqlLog(query, "SELECT", createStringArray("Genre"), createObjectArray());
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Genre.class));
    }
    
    public List<Movie> getMovies(int page, int pageSize, MovieOrder orderBy) {
        String query = "SELECT * FROM Movie "+
                (orderBy != null && !orderBy.getValue().isEmpty() ? "ORDER BY " + orderBy.getValue() + " " : " ") +
                "LIMIT ? OFFSET ?";
        
        String genreQuery = "SELECT * FROM Genre WHERE genre_id IN (SELECT genre_id FROM MovieGenre WHERE movie_id = ?)";
        logger.sqlLog(query, "SELECT", 
                createStringArray("Movie"),
                createObjectArray(pageSize, (page - 1) * pageSize));
        
        
        List<Movie> movies = jdbcTemplate.query(query,
                BeanPropertyRowMapper.newInstance(Movie.class), pageSize, (page - 1) * pageSize);
        
        for (Movie movie : movies) {
            movie.setGenres(jdbcTemplate.query(genreQuery, 
                    BeanPropertyRowMapper.newInstance(Genre.class), movie.getMovieId()));
        }


        logger.sqlLog(genreQuery, "SELECT",
                createStringArray("Genre", "MovieGenre", "Movie"),
                createObjectArray(movies.stream().map(Movie::getMovieId).toArray()));
                
        return movies;
    }
    
    public Movie randomMovie() {
        String query = "SELECT * FROM Movie ORDER BY RAND() LIMIT 1";
        String genreQuery = "SELECT * FROM Genre WHERE genre_id IN (SELECT genre_id FROM MovieGenre WHERE movie_id = ?)";
        
        logger.sqlLog(query, "SELECT", createStringArray("Movie"), createObjectArray());
        
        Movie movie = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Movie.class));
        
        movie.setGenres(jdbcTemplate.query(genreQuery, 
                BeanPropertyRowMapper.newInstance(Genre.class), movie.getMovieId()));
        
        logger.sqlLog(genreQuery, "SELECT",
                createStringArray("Genre", "MovieGenre", "Movie"),
                createObjectArray(movie.getMovieId()));
        
        return movie;
    }
}
