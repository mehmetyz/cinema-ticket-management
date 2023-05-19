package com.sqlcinema.backend.model.request;

import com.sqlcinema.backend.model.Movie;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Jacksonized
@SuperBuilder
public class MovieRequest extends Movie {
    
    @NonNull
    private String[] genreNames;
    
    private Map<String, String> actors;
    private Map<String, String> crews;
    
    private String[] keywords;
}
