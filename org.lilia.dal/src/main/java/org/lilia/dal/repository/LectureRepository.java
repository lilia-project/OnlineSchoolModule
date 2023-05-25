package org.lilia.dal.repository;

import org.lilia.dal.model.Lecture;
import org.lilia.logger.serialization.FilePath;
import org.lilia.logger.serialization.Serializer;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LectureRepository {

    private final List<Lecture> list = new ArrayList<>();

    public void add(Lecture lecture) {
        list.add(lecture);
    }

    public void serializeLecture() {
        Serializer.serialize(list, FilePath.FILE_PATH_LECTURE);
        ConsoleUtils.print(Constants.SERIALIZATION_COMPLETED);
    }

    public void deserializeLecture() {
        String filePath = FilePath.FILE_PATH_LECTURE.getPath();
        Object deserialize = Serializer.deserialize(filePath);
        List<Lecture> lectures = (List<Lecture>) deserialize;
        ConsoleUtils.print(Constants.DESERIALIZATION_COMPLETED);

        list.addAll(lectures);
    }

    public void remove(Lecture lecture) {
        list.remove(lecture);
    }


    public Optional<Lecture> getById(int id) {
        for (Lecture lecture : list) {
            if (lecture.getId() == id) {
                return Optional.of(lecture);
            }
        }
        return Optional.empty();
    }

    public int size() {
        return list.size();
    }

    public void getAll() {
        list.forEach(System.out::println);
    }

    public Optional<Lecture> getByCourseId(int courseId) {
        for (Lecture lecture : list) {
            if (lecture.getCourseId() == courseId) {
                return Optional.of(lecture);
            }
        }
        return Optional.empty();
    }

    public void isBeforeDate(LocalDate localDate) {
        list.stream()
                .filter(lecture -> lecture.getLectureDate().isBefore(localDate))
                .forEach(System.out::println);
    }

    public void isAfterDate(LocalDate localDate) {
        list.stream()
                .filter(lecture -> lecture.getLectureDate().isAfter(localDate))
                .forEach(System.out::println);
    }

    public void isBetweenDate(LocalDate localDate, LocalDate localDateSecond) {
        list.stream()
                .filter(lecture -> lecture.getLectureDate().isAfter(localDate))
                .filter(lecture -> lecture.getLectureDate().isBefore(localDateSecond))
                .forEach(System.out::println);
    }

    public Optional<Lecture> getLectureByEarlyTime() {
        return list.stream()
                .min(Comparator.comparing(Lecture::getLectureDate)
                        .thenComparing(lecture -> {
                            if (lecture.getHomeworkList() != null) {
                                return lecture.getHomeworkList().size();
                            }
                            return null;
                        }, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    public void printLecturesGroupingByTeacher() {
        Map<Integer, List<Lecture>> collect = list.stream()
                .collect(Collectors.groupingBy(Lecture::getPersonId));

        System.out.println(collect);
        // System.out.println(collect.toString().replaceAll("[{}]", " ").replaceAll("\\s", "\n"));
    }
}
