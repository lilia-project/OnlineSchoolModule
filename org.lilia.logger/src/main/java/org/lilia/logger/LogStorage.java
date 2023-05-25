package org.lilia.logger;

import java.util.ArrayList;
import java.util.List;

public class LogStorage {

    private final List<Log> logStorage = new ArrayList<>();
    private final List<LogWriter> logWriters;

    public LogStorage(List<LogWriter> logWriters) {
        this.logWriters = logWriters;
    }

    public void save(Log log) {
        logStorage.add(log);
        for (LogWriter logWriter : logWriters) {
            logWriter.write(log);
        }
    }
}
