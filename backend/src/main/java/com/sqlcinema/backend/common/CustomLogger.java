package com.sqlcinema.backend.common;
import com.sqlcinema.backend.model.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CustomLogger extends Logger {
    private final LogFileProcessor logFileProcessor;
    public CustomLogger(String name, String resourceBundleName, LogFileProcessor logFileProcessor) {
        super(name, resourceBundleName);
        this.logFileProcessor = logFileProcessor;
    }
    public void sqlLog(String sql, Object[] params) {
        
        this.info("SQL: " + sql);
        
        Log log = Log.builder()
                .sql(sql)
                .params(params)
                .timestamp(System.currentTimeMillis())
                .build();
        
        try {
            logFileProcessor.addLog(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Log> getAllLogs() {
        try {
            return logFileProcessor.readAllLogs();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }
    
    public void clearLogs() {
        logFileProcessor.clearLogs();
    }

    public void sqlLog(String query) {
        this.sqlLog(query, new Object[]{});
    }
}
