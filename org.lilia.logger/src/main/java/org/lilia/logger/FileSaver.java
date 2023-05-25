package org.lilia.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileSaver implements LogWriter {

    public static final String LOG_STORAGE_FILE = "src/main/java/org/lilia/log/LogStorageFile";

    private final File file = new File(LOG_STORAGE_FILE);

    @Override
    public void write(Log log) {

        String stringLog = log.toString();
        try (Writer writer = new FileWriter(file, true)) {
            writer.append(stringLog).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
