package org.lilia.logger;

import org.lilia.logger.utils.ConsoleUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;

public class ConfigurationWatcher extends Thread {
    private static final String FILE_DATA_PATH = "src/main/resources/";
    private final ConsoleWriter consoleWriter;
    private final ConfigurationReader configurationReader;

    public ConfigurationWatcher(ConsoleWriter consoleWriter, ConfigurationReader configurationReader) {
        this.consoleWriter = consoleWriter;
        this.configurationReader = configurationReader;
    }

    @Override
    public void run() {

        WatchService watchService;
        try {
            watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get(FILE_DATA_PATH);
            System.out.println(path);

            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WatchKey key;
        while (true) {
            try {
                if ((key = watchService.take()) == null) break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (WatchEvent<?> event : key.pollEvents()) {
                Map<String, String> stringStringMap = configurationReader.readConfiguration();
                String newLogLevel = stringStringMap.get("log.level");
                LogLevel logLevel = LogLevel.valueOf(newLogLevel);
                consoleWriter.setLevel(logLevel);
                ConsoleUtils.print("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
            }
            key.reset();
        }
    }
}
