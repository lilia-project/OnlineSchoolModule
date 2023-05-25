package org.lilia.logger.serialization;

public enum FilePath {
    FILE_PATH_COURSE("COURSE", "src/main/resources/courseBackup.txt"),
    FILE_PATH_LECTURE("LECTURE", "src/main/resources/lectureBackup.txt"),
    FILE_PATH_TEACHER("TEACHER", "src/main/resources/teacherBackup.txt"),
    FILE_PATH_STUDENT("STUDENT", "src/main/resources/studentBackup.txt"),
    FILE_PATH_HOMEWORK("HOMEWORK", "src/main/resources/homeworkBackup.txt"),
    FILE_PATH_ADDITION_MATERIAL("MATERIAL", "src/main/resources/addMaterialBackup.txt"),
    FILE_PATH_LOG_STORAGE_FILE("LOG_STORAGE_FILE", "src/main/java/org/lilia/log/LogStorageFile"),
    FILE_PATH_EMAILS_OF_STUDENTS("EMAILS_OF_STUDENTS", "src/main/resources/emails.txt");

    private final String name;
    private final String path;

    FilePath(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getNumber() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
