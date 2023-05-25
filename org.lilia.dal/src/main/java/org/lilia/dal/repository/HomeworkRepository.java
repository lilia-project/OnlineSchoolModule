package org.lilia.dal.repository;

import org.lilia.dal.model.Homework;
import org.lilia.logger.serialization.FilePath;
import org.lilia.logger.serialization.Serializer;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;

import java.util.*;

public class HomeworkRepository {

    private final Map<Integer, List<Homework>> data = new HashMap<>();

    public void add(Homework homework) {
        List<Homework> value = data.get(homework.getLectureId());
        if (value == null) {
            value = new ArrayList<>();
            value.add(homework);
            data.put(homework.getLectureId(), value);
        } else {
            value.add(homework);
        }
    }

    public List<Homework> getAll() {
        List<Homework> list = new ArrayList<>();
        for (List<Homework> homeworks : data.values()) {
            list.addAll(homeworks);
        }
        return Optional.of(list).orElse(Collections.emptyList());
    }

    public void remove(Homework homework) {
        List<Homework> value = data.get(homework.getLectureId());
        if (value == null) {
            return;
        }
        value.remove(homework);
    }

    public Optional<Homework> getById(int id) {
        Collection<List<Homework>> values = data.values();
        for (List<Homework> homeworks : values) {

            for (Homework homework : homeworks) {

                if (homework.getId() == id) {
                    return Optional.of(homework);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<List<Homework>> getByLectureId(int lectureId) {
        List<Homework> list = data.get(lectureId);
        return Optional.ofNullable(list);
    }

    public void serializeHomework() {
        List<Homework> list = getAll();
        Serializer.serialize(list, FilePath.FILE_PATH_HOMEWORK);
        ConsoleUtils.print(Constants.SERIALIZATION_COMPLETED);
    }

    public void deserialize() {
        String filePath = FilePath.FILE_PATH_HOMEWORK.getPath();
        Object deserialize = Serializer.deserialize(filePath);
        List<Homework> homeworks = (List<Homework>) deserialize;
        ConsoleUtils.print(Constants.DESERIALIZATION_COMPLETED);

        for (Homework homework : homeworks) {
            saveHomework(homework);
        }
    }

    private void saveHomework(Homework homework) {
        if (data.containsKey(homework.getLectureId())) {
            List<Homework> list = data.get(homework.getLectureId());
            list.add(homework);
        } else {
            List<Homework> list = new ArrayList<>();
            list.add(homework);
            data.put(homework.getLectureId(), list);
        }
    }
}
