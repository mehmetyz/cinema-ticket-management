package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.common.CustomLogger;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dev/log")
@AllArgsConstructor
public class LogController {
    private final CustomLogger customLogger;
    
    @GetMapping("/all")
    public Object getAllLogs() {
        return customLogger.getAllLogs();
    }
    
    @DeleteMapping("/clear")
    public void clearLogs() {
        customLogger.clearLogs();
    }
}
