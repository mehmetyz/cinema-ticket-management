package com.sqlcinema.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqlcinema.backend.model.Genre;
import com.sqlcinema.backend.model.Movie;
import com.sqlcinema.backend.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
@AllArgsConstructor
public class MovieController {
    
    private final MovieService movieService;
    
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.ok(movieService.getGenres());
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Movie>> getMovies(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(movieService.getMovies(page, size));
    }
    
    @GetMapping("/popular")
    public ResponseEntity<List<Movie>> getPopularMovies(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(movieService.getPopularMovies(page, size));
    }
    
    @GetMapping("/newest")
    public ResponseEntity<List<Movie>> getNewestMovies(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(movieService.getNewestMovies(page, size));
    }
    
    @GetMapping("/random")
    public ResponseEntity<Movie> randomMovie() {
        return ResponseEntity.ok(movieService.randomMovie());
    }
}
