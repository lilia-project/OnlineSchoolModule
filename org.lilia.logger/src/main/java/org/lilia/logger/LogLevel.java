package org.lilia.logger;

public enum LogLevel {
    OFF("OFF", 4),
    ERROR("ERROR", 3),
    WARNING("WARNING", 2),
    INFO("INFO", 1),
    DEBUG("DEBUG", 0);

    private final String field;

    private final int levelId;

    LogLevel(String field, int levelId) {
        this.field = field;
        this.levelId = levelId;
    }

    public String getField() {
        return field;
    }

    public int getLevelId() {
        return levelId;
    }
}
