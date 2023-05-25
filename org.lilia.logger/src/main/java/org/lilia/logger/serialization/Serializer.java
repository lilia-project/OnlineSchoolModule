package org.lilia.logger.serialization;

import java.io.*;

public class Serializer {

    public static <T> void serialize(T type, FilePath filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.getPath());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Object deserialize(String filePath) {

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
