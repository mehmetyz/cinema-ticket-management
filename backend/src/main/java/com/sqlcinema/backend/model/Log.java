package com.sqlcinema.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data                                                               
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = Log.LogBuilder.class)
public class Log {
    @JsonProperty("sql")
    private String sql;
    
    @JsonProperty("params")
    private Object[] params;
    
    @JsonProperty("timestamp")
    private long timestamp;
}
