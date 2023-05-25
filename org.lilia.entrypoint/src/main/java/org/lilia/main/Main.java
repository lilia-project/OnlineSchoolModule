package org.lilia.main;

import org.lilia.api.network.SelectorClient;
import org.lilia.api.network.SelectorServer;
import org.lilia.api.view.*;
import org.lilia.dal.repository.*;
import org.lilia.logger.ConfigurationReader;
import org.lilia.logger.ConfigurationWatcher;
import org.lilia.logger.LogService;
import org.lilia.logger.LoggerFactory;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;
import org.lilia.logger.exception.NoSuchMaterialIdException;
import org.lilia.service.service.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws NoSuchMaterialIdException, InterruptedException {

        LectureRepository lectureRepository = new LectureRepository();
        HomeworkRepository homeworkRepository = new HomeworkRepository();
        HomeworkService homeworkService = new HomeworkService(homeworkRepository);
        AdditionalMaterialRepository additionalMaterialRepository = new AdditionalMaterialRepository();
        AdditionalMaterialService additionalMaterialService = new AdditionalMaterialService(additionalMaterialRepository);
        LectureService lectureService = new LectureService(lectureRepository, homeworkService);
        CourseRepository courseRepository = new CourseRepository();
        CourseService courseService = new CourseService(courseRepository, lectureService);
        PersonRepository personRepository = new PersonRepository();
        PersonService personService = new PersonService(personRepository);
        LectureView lectureView = new LectureView();
        HomeworkView homeworkView = new HomeworkView(lectureService);
        AdditionalMaterialView additionalMaterialView = new AdditionalMaterialView(lectureService);
        CourseView courseView = new CourseView();
        PersonView personView = new PersonView(courseService);
        ControlWorkService controlWorkService = new ControlWorkService();
        ConfigurationReader configurationReader = new ConfigurationReader();

        ConfigurationWatcher configurationWatcher = new ConfigurationWatcher(LoggerFactory.CONSOLE_WRITER, configurationReader);

        ConsoleUtils.print("\nWelcome to Online school!");

        configurationWatcher.setDaemon(true);
        configurationWatcher.start();

        ConsoleUtils.print(Constants.CONTINUE);
        String userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

        while (userChoice.equalsIgnoreCase("Y")) {

            int category = ConsoleUtils.choiceCategory();

            switch (category) {
                case 1 -> {
                    ConsoleUtils.print(Constants.ACTION);
                    courseView.workWithCourse(courseService);
                }
                case 2 -> {
                    ConsoleUtils.print(Constants.ACTION);
                    lectureView.workWithLectures(lectureService);
                }
                case 3 -> {
                    ConsoleUtils.print(Constants.ACTION);
                    personView.workWithPerson(personService);
                }
                case 4 -> {
                    ConsoleUtils.print(Constants.ACTION);
                    homeworkView.workWithHomework(homeworkService);
                }
                case 5 -> {
                    ConsoleUtils.print(Constants.ACTION);
                    additionalMaterialView.workWithAdditionalMaterials(additionalMaterialService);
                }
                case 6 -> controlWorkService.startControlWork();
                case 7 -> startServer();
                case 8 -> startClient();
                case 9 -> {
                    LogService.filterLogStorageFile();
                    LogService.filterHalfLogStorageFile();
                }
                case 0 -> ConsoleUtils.print("Do you want finish or ");
                default -> ConsoleUtils.print(Constants.ERROR + "incompatible symbol");
            }
            ConsoleUtils.print(Constants.CONTINUE);
            userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
        }

        ConsoleUtils.exit();
    }

    private static void startServer() {
        Thread serverThread = new Thread(() -> {
            try {
                new SelectorServer().start();
                ConsoleUtils.print("Server started");
            } catch (IOException e) {
                ConsoleUtils.print("Server is not able to start, details: " + e.getMessage());
            }
        });
        serverThread.start();
    }

    private static void startClient() throws InterruptedException {
        Thread clientThread = new Thread(() -> {
            try {
                new SelectorClient().start();
                ConsoleUtils.print("Client started");
            } catch (IOException e) {
                ConsoleUtils.print("Client is not able to start, details: " + e.getMessage());
            }
        });
        clientThread.start();
        clientThread.join(10000);
        clientThread.interrupt();
    }
}
