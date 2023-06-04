package com.sqlcinema.backend.model.response;

import com.sqlcinema.backend.model.Theatre;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TheatreResponse extends Theatre {
    private int seatSize;
}
