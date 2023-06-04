package com.sqlcinema.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;


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
    
    @JsonProperty("count")
    private int count;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Log log)) return false;
        return sql.equals(log.sql) && Arrays.equals(params, log.params);
    }
}
