package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.manager.ActivityManager;
import com.sqlcinema.backend.model.UserAccount;
import com.sqlcinema.backend.model.activity.ActivityType;
import com.sqlcinema.backend.model.movie.Genre;
import com.sqlcinema.backend.model.movie.Movie;
import com.sqlcinema.backend.model.movie.MovieComment;
import com.sqlcinema.backend.model.movie.Person;
import com.sqlcinema.backend.model.request.MovieRequest;
import com.sqlcinema.backend.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sqlcinema.backend.common.Constants.getCurrentUser;

@RestController
@RequestMapping("/api/movie")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final ActivityManager activityManager;
    
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.ok(movieService.getGenres());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getMovies(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "0") int genreId) {
        return ResponseEntity.ok(movieService.getMovies(page, size, genreId));
    }
    
    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable("movieId") int movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }
    
    @GetMapping("/popular")
    public ResponseEntity<List<Movie>> getPopularMovies(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "0") int genreId) {
        return ResponseEntity.ok(movieService.getPopularMovies(page, size, genreId));
    }

    @GetMapping("/newest")
    public ResponseEntity<List<Movie>> getNewestMovies(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "0") int genreId) {
        return ResponseEntity.ok(movieService.getNewestMovies(page, size, genreId));
    }

    @GetMapping("/random")
    public ResponseEntity<Movie> randomMovie() {
        return ResponseEntity.ok(movieService.randomMovie());
    }
    
    
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "") String query) {
        return ResponseEntity.ok(movieService.searchMovies(page, size, '%' + query + '%'));
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getMovieCount(@RequestParam( name = "genre_id", defaultValue = "0") int genreId) {
        return ResponseEntity.ok(movieService.getMovieCount(genreId));
    }
    
    @GetMapping("/count/search")
    public ResponseEntity<Integer> getMovieCount(@RequestParam( name = "query", defaultValue = "") String query) {
        return ResponseEntity.ok(movieService.getMovieCount('%' + query + '%'));
    }
    
    
    @PostMapping("/")                                                                   
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Movie> addMovie(@RequestBody MovieRequest movie) {

        System.out.println("Movie: " + movie);
        
        int movieId = movieService.addMovie(movie);
        activityManager.addActivity(getCurrentUser().getUserId(), 
                ActivityType.CREATE, "Movie " + movie.getTitle() + " added");
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }
    
    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable("movieId") int movieId) {
        Movie movie = movieService.getMovie(movieId);
        movieService.deleteMovie(movieId);
        activityManager.addActivity(getCurrentUser().getUserId(), 
                ActivityType.DELETE, "Movie " + movie.getTitle() + " deleted");
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{movieId}")
    @PreAuthorize("hasAuthority('ADMIN')")   
    public ResponseEntity<Movie> updateMovie(@PathVariable("movieId") int movieId, @RequestBody MovieRequest movie) {
        movieService.updateMovie(movieId, movie);
        activityManager.addActivity(getCurrentUser().getUserId(), 
                ActivityType.UPDATE, "Movie " + movie.getTitle() + " updated");
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }
    
    
    @GetMapping ("/{movieId}/cast")
    public ResponseEntity<List<Person>> getCast(@PathVariable("movieId") int movieId) {
        List<Person> res = movieService.getCast(movieId);
        return ResponseEntity.ok(res);
    }
    
    @GetMapping ("/{movieId}/comments")
    public ResponseEntity<List<MovieComment>> getComments(@PathVariable("movieId") int movieId,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        List<MovieComment> res = movieService.getComments(movieId, page, size);
        return ResponseEntity.ok(res);
    }
    
    
    @GetMapping ("/{movieId}/keywords")
    public ResponseEntity<String> getKeywords(@PathVariable("movieId") int movieId) {
        return ResponseEntity.ok(movieService.getKeywords(movieId));
    }
    
}
