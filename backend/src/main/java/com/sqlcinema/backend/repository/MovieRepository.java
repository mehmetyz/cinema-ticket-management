package com.sqlcinema.backend.repository;

import com.sqlcinema.backend.common.CustomLogger;
import com.sqlcinema.backend.model.Genre;
import com.sqlcinema.backend.model.Movie;
import com.sqlcinema.backend.model.order.MovieOrder;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.sqlcinema.backend.common.Constants.createObjectArray;

@Repository
@AllArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomLogger logger;

    public List<Genre> getGenres() {
        String query = "SELECT * FROM Genre";
        logger.sqlLog(query);
        try {
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Genre.class));
        } catch (DataAccessException ignored) {
            return new ArrayList<>();
        }
    }

    public List<Movie> getMovies(int page, int pageSize, int genreId, MovieOrder orderBy) {
        String query = "SELECT Movie.* FROM Movie " +
                (genreId != 0 ? "INNER JOIN MovieGenre ON Movie.movie_id = MovieGenre.movie_id " : "") +
                (genreId != 0 ? "WHERE MovieGenre.genre_id = ? " : "") +
                (orderBy != null && !orderBy.getValue().isEmpty() ? "ORDER BY " + orderBy.getValue() + " " : " ") +
                "LIMIT ? OFFSET ?";

        String genreQuery = "SELECT * FROM Genre WHERE genre_id IN (SELECT genre_id FROM MovieGenre WHERE movie_id = ?)";
        logger.sqlLog(query);

        List<Integer> params = new ArrayList<>();
        if (genreId != 0) {
            params.add(genreId);
        }
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        List<Movie> movies = jdbcTemplate.query(query,
                BeanPropertyRowMapper.newInstance(Movie.class),
                params.toArray());
        
        
        for (Movie movie : movies) {
            movie.setGenres(jdbcTemplate.query(genreQuery,
                    BeanPropertyRowMapper.newInstance(Genre.class), movie.getMovieId()));
        }
        
        logger.sqlLog(genreQuery);
        return genreId == 0 ? movies : movies.stream()
                .filter(movie -> movie.getGenres().stream()
                        .anyMatch(genre -> genre.getGenreId() == genreId))
                .toList();
    }
    
    public Movie getMovieById(int id) {
        try {
            String query = "SELECT * FROM Movie WHERE movie_id = ?";
            logger.sqlLog(query, createObjectArray(id));
            return jdbcTemplate.queryForObject(query,
                    BeanPropertyRowMapper.newInstance(Movie.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    public Movie randomMovie() {
        String query = "SELECT * FROM Movie ORDER BY RAND() LIMIT 1";
        String genreQuery = "SELECT * FROM Genre WHERE genre_id IN (SELECT genre_id FROM MovieGenre WHERE movie_id = ?)";

        logger.sqlLog(query);
        
        Movie movie = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Movie.class));
        
        if (movie == null) {
            return null;
        }

        movie.setGenres(jdbcTemplate.query(genreQuery,
                BeanPropertyRowMapper.newInstance(Genre.class), movie.getMovieId()));

        logger.sqlLog(genreQuery, createObjectArray(movie.getMovieId()));
        return movie;
    }

    public List<Movie> searchMovies(int page, int size, String query) {
        String searchQuery = "SELECT * FROM Movie m INNER JOIN KeywordSet k ON m.movie_id = k.movie_id " +
                "WHERE k.keywords LIKE ? " +
                "OR m.title LIKE ? " +
                "OR m.overview LIKE ? " +
                " LIMIT ? OFFSET ?";

        logger.info(searchQuery);

        List<Movie> movies = jdbcTemplate.query(searchQuery,
                BeanPropertyRowMapper.newInstance(Movie.class), query, query, query, size, (page - 1) * size);

        String genreQuery = "SELECT * FROM Genre WHERE genre_id IN (SELECT genre_id FROM MovieGenre WHERE movie_id = ?)";

        for (Movie movie : movies) {
            movie.setGenres(jdbcTemplate.query(genreQuery,
                    BeanPropertyRowMapper.newInstance(Genre.class), movie.getMovieId()));
        }
        
        logger.info(genreQuery);
        return movies;
    }
    
    public void addMovie(Movie movie) {
        
    }

    public int getMovieCount(int genreId) {
        String query = "SELECT COUNT(*) FROM Movie " +
                (genreId != 0 ? "INNER JOIN MovieGenre ON Movie.movie_id = MovieGenre.movie_id " : "") +
                (genreId != 0 ? "WHERE MovieGenre.genre_id = ? " : "");

        logger.sqlLog(query);
        return genreId == 0 ? jdbcTemplate.queryForObject(query, Integer.class) :
                jdbcTemplate.queryForObject(query, Integer.class, genreId);
    }

    public int getMovieCountByQuery(String query) {
        String searchQuery = "SELECT COUNT(*) FROM Movie m INNER JOIN KeywordSet k ON m.movie_id = k.movie_id " +
                "WHERE k.keywords LIKE ? " +
                "OR m.title LIKE ? " +
                "OR m.overview LIKE ?";

        logger.sqlLog(searchQuery);
        return jdbcTemplate.queryForObject(searchQuery, Integer.class, query, query, query);
    }
}
