package com.sqlcinema.backend.common;
import com.sqlcinema.backend.model.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CustomLogger {
    private final LogFileProcessor logFileProcessor;
    private final Logger logger = Logger.getLogger(getClass().getPackageName());
    public CustomLogger(LogFileProcessor logFileProcessor) {
        this.logFileProcessor = logFileProcessor;
    }
    public void sqlLog(String sql, Object[] params) {
        Log log = Log.builder()
                .sql(sql)
                .params(params)
                .timestamp(System.currentTimeMillis())
                .count(1)
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
    
    public void info(String message) {
        logger.info(message);
    }
}
