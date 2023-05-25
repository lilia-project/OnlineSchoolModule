package org.lilia.logger;

public class Logger {
    private final String className;
    private final LogStorage logStorage;

    public Logger(String className, LogStorage logStorage) {
        this.className = className;
        this.logStorage = logStorage;
    }

    public void error(String message, Throwable throwable) {
        saveLog(message, throwable, LogLevel.ERROR);
    }

    public void error(String message) {
        saveLog(message, null, LogLevel.ERROR);
    }

    public void warning(String message, Throwable throwable) {
        saveLog(message, throwable, LogLevel.WARNING);
    }

    public void warning(String message) {
        saveLog(message, null, LogLevel.WARNING);
    }

    public void info(String message) {
        saveLog(message, null, LogLevel.INFO);
    }

    public void debug(String message) {
        saveLog(message, null, LogLevel.DEBUG);
    }

    private void saveLog(String message, Throwable throwable, LogLevel logLevel) {
        Log log;
        if (throwable == null) {
            log = new Log(className, logLevel.getField(), message);
        } else {
            log = new Log(className, logLevel.getField(), message, throwable.toString());
        }
        logStorage.save(log);
    }
}
