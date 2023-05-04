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
    
    @JsonProperty("method")
    private String method;
    
    @JsonProperty("tableName")
    private String[] tableName;
    
    @JsonProperty("params")
    private Object[] params;
}
