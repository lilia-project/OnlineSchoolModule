package org.lilia.logger;

import org.lilia.logger.utils.ConsoleUtils;

import java.util.Map;

public class ConsoleWriter implements LogWriter {

    private LogLevel level;


    public ConsoleWriter() {
        ConfigurationReader configurationReader = new ConfigurationReader();
        Map<String, String> map = configurationReader.readConfiguration();
        String value = map.get("log.level");
        level = LogLevel.valueOf(value);
    }

    @Override
    public void write(Log log) {
        if (level.getLevelId() <= LogLevel.valueOf(log.getLogLevel()).getLevelId()) {
            if (log.getLogLevel().equals(LogLevel.ERROR.getField())) {
                System.err.println(log);
            } else {
                System.out.println(log);
            }
        }
    }

    public void setLevel(LogLevel level) {
        if (this.level != level) {
            ConsoleUtils.print("LogLevel change from " + this.level + " to " + level);
        }
        this.level = level;
    }
}
