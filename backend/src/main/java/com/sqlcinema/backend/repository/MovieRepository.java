package com.sqlcinema.backend.repository;

import com.sqlcinema.backend.common.CustomLogger;
import com.sqlcinema.backend.model.movie.*;
import com.sqlcinema.backend.model.order.MovieOrder;
import com.sqlcinema.backend.model.request.MovieRequest;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            Movie movie = jdbcTemplate.queryForObject(query,
                    BeanPropertyRowMapper.newInstance(Movie.class), id);

            if (movie == null) {
                return null;
            }

            String genreQuery = "SELECT * FROM Genre WHERE genre_id IN (SELECT genre_id FROM MovieGenre WHERE movie_id = ?)";
            logger.sqlLog(genreQuery, createObjectArray(id));

            movie.setGenres(jdbcTemplate.query(genreQuery,
                    BeanPropertyRowMapper.newInstance(Genre.class), movie.getMovieId()));

            return movie;

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
        
        List<Movie> movies = jdbcTemplate.query(searchQuery,
                BeanPropertyRowMapper.newInstance(Movie.class), query, query, query, size, (page - 1) * size);

        String genreQuery = "SELECT * FROM Genre WHERE genre_id IN (SELECT genre_id FROM MovieGenre WHERE movie_id = ?)";

        for (Movie movie : movies) {
            movie.setGenres(jdbcTemplate.query(genreQuery,
                    BeanPropertyRowMapper.newInstance(Genre.class), movie.getMovieId()));
        }

        return movies;
    }

    public int addMovie(MovieRequest movie) {
        String query = "CALL create_movie(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.sqlLog(query, createObjectArray(movie.toString()));

        jdbcTemplate.update(query, movie.getTitle(), movie.getRuntime(), movie.getOverview(), movie.getPosterPath(),
                movie.getBackdropPath(), movie.getReleaseDate(), movie.getRating(), movie.getTrailerLink(),
                movie.getCountry(), movie.getLanguage(), movie.getGenres().get(0).getName());

        for (int i = 1; i < movie.getGenres().size(); i++) {
            query = "CALL assign_movie_genre(?, ?)";
            logger.sqlLog(query, createObjectArray(movie.getTitle(), movie.getGenres().get(i).getName()));
            jdbcTemplate.update(query, movie.getTitle(), movie.getGenres().get(i).getName());
        }


        if (movie.getActors() != null) {
            for (Map.Entry<String, String> actor : movie.getActors().entrySet()) {
                query = "CALL assign_actor(?, ?, ?)";
                logger.sqlLog(query, createObjectArray(movie.getTitle(), actor.getKey(), actor.getValue()));
                jdbcTemplate.update(query, movie.getTitle(), actor.getKey(), actor.getValue());
            }
        }

        if (movie.getCrews() != null) {
            for (Map.Entry<String, String> crew : movie.getCrews().entrySet()) {
                query = "CALL assign_crew(?, ?, ?)";
                logger.sqlLog(query, createObjectArray(movie.getTitle(), crew.getKey(), crew.getValue()));
                jdbcTemplate.update(query, movie.getTitle(), crew.getKey(), crew.getValue());
            }
        }

        if (movie.getKeywords() != null) {
            String keywords = String.join(",", movie.getKeywords());
            query = "CALL create_keyword_set(?, ?, TRUE)";
            logger.sqlLog(query, createObjectArray(movie.getTitle(), keywords));
            jdbcTemplate.update(query, movie.getTitle(), keywords);
        }

        String getMovieId = "SELECT movie_id FROM Movie WHERE title = ?";
        logger.sqlLog(getMovieId, createObjectArray(movie.getTitle()));

        return jdbcTemplate.queryForObject(getMovieId, Integer.class, movie.getTitle());
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
        String searchQuery = "SELECT COUNT(*) FROM Movie m LEFT JOIN KeywordSet k ON m.movie_id = k.movie_id " +
                "WHERE k.keywords LIKE ? " +
                "OR m.title LIKE ? " +
                "OR m.overview LIKE ?";

        logger.sqlLog(searchQuery);
        return jdbcTemplate.queryForObject(searchQuery, Integer.class, query, query, query);
    }

    public void deleteMovie(int movieId) {
        String query = "CALL delete_movie(?)";
        logger.sqlLog(query, createObjectArray(movieId));
        jdbcTemplate.update(query, movieId);
    }

    public void updateMovie(int movieId, MovieRequest movie) {
        logger.info("Updating movie with id: " + movieId);
        
        String query = "CALL update_movie(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.sqlLog(query, createObjectArray(movieId, movie.toString()));

        jdbcTemplate.update(query, movieId, movie.getTitle(), movie.getRuntime(), movie.getOverview(), movie.getPosterPath(),
                movie.getBackdropPath(), movie.getReleaseDate(), movie.getTrailerLink(),
                movie.getCountry(), movie.getLanguage());

        query = "DELETE FROM MovieGenre WHERE movie_id = ?";
        logger.sqlLog(query, createObjectArray(movieId));
        jdbcTemplate.update(query, movieId);
        
        for (Genre genre : movie.getGenres()) {
            query = "CALL assign_movie_genre(?, ?)";
            logger.sqlLog(query, createObjectArray(movieId, genre.getName()));
            jdbcTemplate.update(query, movie.getTitle(), genre.getName());
        }
        
        if (movie.getKeywords() != null) {
            String keywords = String.join(",", movie.getKeywords());
            query = "CALL create_keyword_set(?, ?, FALSE)";
            logger.sqlLog(query, createObjectArray(movie.getTitle(), keywords));
            jdbcTemplate.update(query, movie.getTitle(), keywords);
        }
    }

    public List<Person> getPeople(int movieId) {

        List<Person> cast = new ArrayList<>();
        String actorQuery = "SELECT * FROM ActorCredit INNER JOIN Person " +
                "ON ActorCredit.person_id = Person.person_id WHERE movie_id = ?";

        logger.sqlLog(actorQuery, createObjectArray(movieId));

        String crewQuery = "SELECT * FROM CrewCredit INNER JOIN Person " +
                "ON CrewCredit.person_id = Person.person_id WHERE movie_id = ?";
        logger.sqlLog(crewQuery, createObjectArray(movieId));

        try {
            cast.addAll(jdbcTemplate.query(actorQuery, BeanPropertyRowMapper.newInstance(Cast.class), movieId));
        } catch (Exception ignored) {
        }

        try {
            cast.addAll(jdbcTemplate.query(crewQuery, BeanPropertyRowMapper.newInstance(Crew.class), movieId));
        } catch (Exception ignored) {
        }

        return cast;
    }

    public List<MovieComment> getComments(int movieId, int page, int size) {
        String query = "SELECT m.*, ua.username FROM UserMovieComment m INNER JOIN UserAccount ua " +
                "ON m.user_id = ua.user_id WHERE m.movie_id = ? LIMIT ? OFFSET ?";
        logger.sqlLog(query, createObjectArray(movieId, size, (page - 1) * size));
        try {
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(MovieComment.class), movieId, size, (page - 1) * size);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String getKeywords(int movieId) {
        String query = "SELECT keywords FROM KeywordSet WHERE movie_id = ?";
        logger.sqlLog(query, createObjectArray(movieId));
        try {
            return jdbcTemplate.queryForObject(query, String.class, movieId);
        } catch (Exception e) {
            return null;
        }
    }
}
