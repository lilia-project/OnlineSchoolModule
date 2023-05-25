package org.lilia.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private final String name;

    private final String logLevel;

    private final String message;
    private final LocalDateTime createdAt;
    DateTimeFormatter dTF = DateTimeFormatter.ofPattern("d-MM-yyyy HH:mm:ss:SSS");
    private String stackTrace;

    protected Log(String name, String logLevel, String message, String stackTrace) {
        this.name = name;
        this.logLevel = logLevel;
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.stackTrace = stackTrace;
    }

    protected Log(String name, String logLevel, String message) {
        this.name = name;
        this.logLevel = logLevel;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String log = createdAt.format(dTF) +
                " " + logLevel +
                " " + name +
                " " + message;
        if (stackTrace != null) {
            return log + " " + stackTrace;
        }
        return log;
    }

    public String getLogLevel() {
        return logLevel;
    }
}

