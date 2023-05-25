package org.lilia.service.service;

import org.lilia.dal.model.Homework;
import org.lilia.dal.repository.HomeworkRepository;
import org.lilia.logger.exception.NoSuchHomeworkException;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;
import org.lilia.service.dto.HomeworkDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HomeworkService {
    private final HomeworkRepository homeworkRepository;

    public HomeworkService(HomeworkRepository homeworkRepository) {
        this.homeworkRepository = homeworkRepository;
    }

    public void createHomework(int lectureId, String task) {
        if (task == null) {
            throw new IllegalArgumentException("homework name is null");
        }
        Homework homework = new Homework(lectureId, task);
        homeworkRepository.add(homework);
    }

    public HomeworkDto createHomeworkDto(String task) {
        return new HomeworkDto(task);
    }

    public Homework updateHomework(Homework homework, HomeworkDto homeworkDto) {
        if ((homeworkDto.getTask()) != null) {
            homework.setTask(homeworkDto.getTask());
        }
        return homework;
    }

    public Homework getRequireById(int homeworkId) {

        Optional<Homework> homework = homeworkRepository.getById(homeworkId);
        if (homework.isEmpty()) {
            throw new NoSuchHomeworkException(homeworkId);
        }
        return homework.get();
    }

    public List<Homework> findAllByLectureId(int lectureId) {
        Optional<List<Homework>> byLectureId = homeworkRepository.getByLectureId(lectureId);
        return byLectureId.orElse(Collections.emptyList());
    }

    public void deleteById(int homeworkId) {
        Optional<Homework> homework = homeworkRepository.getById(homeworkId);
        if (homework.isEmpty()) {
            ConsoleUtils.print(Constants.ELEMENT_NOT_EXIST);
            throw new NoSuchHomeworkException(homeworkId);
        } else {
            homeworkRepository.remove(homework.get());
        }
        ConsoleUtils.print(Constants.ELEMENT_DELETED);
    }

    public int homeworkIdIsValid() {
        int homeworkId = ConsoleUtils.readInteger();
        Optional<Homework> homework = homeworkRepository.getById(homeworkId);
        while (homework.isEmpty()) {
            ConsoleUtils.print(Constants.ELEMENT_NOT_EXIST);
            homeworkId = ConsoleUtils.readInteger();
            homework = homeworkRepository.getById(homeworkId);
        }
        return homeworkId;
    }

    public void backupHomework() {
        homeworkRepository.serializeHomework();

    }

    public void deserialization() {
        homeworkRepository.deserialize();

    }
}


