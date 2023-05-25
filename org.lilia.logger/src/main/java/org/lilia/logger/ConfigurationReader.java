package org.lilia.logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationReader {
    private static final String FILE_DATA_PATH = "src/main/resources/valueLogLevel.properties";

    public Map<String, String> readConfiguration() {

        Map<String, String> mapConfiguration = new HashMap<>();

        Path path = Paths.get(FILE_DATA_PATH);

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                Pair pair = parseLine(currentLine);
                mapConfiguration.put(pair.getKey(), pair.getValue());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return mapConfiguration;
    }

    private Pair parseLine(String line) {

        String[] str = line.split("=");
        String key = str[0];
        String value = str[1];
        return new Pair(key, value);
    }

    static class Pair {
        private final String key;
        private final String value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
