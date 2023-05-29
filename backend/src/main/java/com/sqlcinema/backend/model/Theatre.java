package com.sqlcinema.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Theatre {
    private int theatreId;
    private String name;
    private int isAvailable;
}
