package org.lilia.api.view;

import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;
import org.lilia.service.dto.LectureDto;
import org.lilia.logger.Logger;
import org.lilia.logger.LoggerFactory;
import org.lilia.dal.model.Lecture;
import org.lilia.service.service.LectureService;

import java.time.LocalDate;

public class LectureView {
    private static final Logger logger = LoggerFactory.getLogger(LectureView.class);

    private static LocalDate getLocalDate() {
        ConsoleUtils.print(Constants.FIRST_DATE_FOR_LECTURE);

        ConsoleUtils.print(Constants.INPUT_DAY);
        int date = Integer.parseInt(ConsoleUtils.readAndValidationInput("\\d{2}"));

        ConsoleUtils.print(Constants.INPUT_MONTHS);
        int months = Integer.parseInt(ConsoleUtils.readAndValidationInput("\\d{2}"));

        if (months < 1 | months > 12) {
            ConsoleUtils.print(Constants.REPEAT_INPUT);
            months = Integer.parseInt(ConsoleUtils.readAndValidationInput("\\d{2}"));
        }

        ConsoleUtils.print(Constants.INPUT_YEAR);
        int year = Integer.parseInt(ConsoleUtils.readAndValidationInput("\\d{4}"));

        LocalDate localDate = LocalDate.of(year, months, date);
        return localDate;
    }

    private static void outputByDateParameter(LectureService lectureService, LocalDate localDate) {
        switch (ConsoleUtils.choiceDisplayType()) {
            case 1 -> lectureService.isBeforeDate(localDate);
            case 2 -> lectureService.isAfterDate(localDate);
            case 3 -> {
                LocalDate localDateSecond = getLocalDateSecond();
                lectureService.isBetweenDates(localDate, localDateSecond);
            }
        }
    }

    private static LocalDate getLocalDateSecond() {
        ConsoleUtils.print(Constants.SECOND_DATE_FOR_LECTURE_BETWEEN_DATES);

        ConsoleUtils.print(Constants.INPUT_DAY);
        int dateSecond = Integer.parseInt(ConsoleUtils.readAndValidationInput("\\d{2}"));

        ConsoleUtils.print(Constants.INPUT_MONTHS);
        int monthsSecond = Integer.parseInt(ConsoleUtils.readAndValidationInput("\\d{2}"));

        ConsoleUtils.print(Constants.INPUT_YEAR);
        int yearSecond = Integer.parseInt(ConsoleUtils.readAndValidationInput("\\d{4}"));

        LocalDate localDateSecond = LocalDate.of(yearSecond, monthsSecond, dateSecond);
        return localDateSecond;
    }

    private static void editLecture(LectureService lectureService, Lecture lecture) {
        ConsoleUtils.print(Constants.NAME);
        String lectureName = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);

        ConsoleUtils.print(Constants.DESCRIPTION);
        String lectureDescription = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);

        System.out.println("choose from available course id");

        String courseId = ConsoleUtils.readAndValidationInput(Constants.NUMBER);

        ConsoleUtils.print(Constants.TEACHER_ID);
        String personId = ConsoleUtils.readAndValidationInput(Constants.NUMBER);

        LectureDto lectureDto = lectureService.createLectureDto(Integer.parseInt(courseId), lectureName, lectureDescription, Integer.parseInt(personId));

        Lecture lectureUpdate = lectureService.updateLecture(lecture, lectureDto);
        System.out.println(lectureUpdate);
    }

    private static Lecture getLectureById(LectureService lectureService) {
        ConsoleUtils.print(Constants.LECTURE_ID);
        int lectureId = ConsoleUtils.readInteger();
        return lectureService.getRequireById(lectureId);
    }

    private static Lecture createNewLecture(LectureService lectureService) {
        ConsoleUtils.print(Constants.NAME);
        String lectureName = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);
        return lectureService.createLecture(lectureName);
    }

    private static void deleteLecture(LectureService lectureService) {
        ConsoleUtils.print(Constants.LECTURE_ID);
        int lectureId = lectureService.lectureIdIsValid();
        lectureService.deleteById(lectureId);
    }

    public void workWithLectures(LectureService lectureService) {
        logger.info("work with lecture section");

        String userChoice = "Y";
        while (userChoice.equalsIgnoreCase("Y")) {
            switch (ConsoleUtils.choiceAction()) {
                case 1 -> {
                    logger.info("selected to create lecture");
                    while (userChoice.equalsIgnoreCase("Y")) {

                        Lecture lecture = createNewLecture(lectureService);
                        logger.info("lecture created successful" + lecture.getId());

                        ConsoleUtils.print(Constants.CREATE_NEW);
                        userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
                    }
                }
                case 2 -> {
                    Lecture lecture = getLectureById(lectureService);
                    logger.info("found lecture " + lecture);

                    ConsoleUtils.print(Constants.ELEMENT_EDIT);
                    userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

                    while (userChoice.equalsIgnoreCase("Y")) {

                        editLecture(lectureService, lecture);

                        ConsoleUtils.print(Constants.ELEMENT_EDIT);
                        userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
                    }
                }
                case 3 -> {
                    ConsoleUtils.print(Constants.DISPLAY_TYPE);

                    switch (ConsoleUtils.outputDate()) {
                        case 1 -> lectureService.outputAll();
                        case 2 -> {
                            LocalDate localDate = getLocalDate();
                            outputByDateParameter(lectureService, localDate);
                        }
                        case 3 -> lectureService.getLectureInEarlyTime();
                        case 4 -> lectureService.printLecturesGrouping();
                        case 0 -> ConsoleUtils.print(Constants.EXIT);
                        default -> {
                            logger.error(Constants.ERROR);
                            ConsoleUtils.print(Constants.ERROR);
                        }
                    }
                }
                case 4 -> {
                    logger.info("selected delete lecture");
                    deleteLecture(lectureService);
                    logger.info("lecture deleted successful");
                }
                case 5 -> lectureService.backupLecture();
                case 6 -> lectureService.deserialize();
                case 7 -> {
                    logger.info("selected EXIT from menu");
                    ConsoleUtils.print(Constants.EXIT);
                }
                default -> {
                    logger.error(Constants.ERROR);
                    ConsoleUtils.print(Constants.ERROR);
                }
            }
            ConsoleUtils.print(Constants.STAY_IN);
            userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
        }
    }

}
