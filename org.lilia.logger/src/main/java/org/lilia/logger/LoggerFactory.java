package org.lilia.logger;

import java.util.List;

public final class LoggerFactory {

    public static final ConsoleWriter CONSOLE_WRITER = new ConsoleWriter();
    private static final LogStorage LOG_STORAGE = new LogStorage(List.of(new FileSaver(), CONSOLE_WRITER));

    private LoggerFactory() {
    }

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = new Logger(clazz.getName(), LOG_STORAGE);
        return logger;
    }
}
