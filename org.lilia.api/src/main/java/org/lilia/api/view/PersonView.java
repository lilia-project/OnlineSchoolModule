package org.lilia.api.view;

import org.lilia.dal.model.Person;
import org.lilia.dal.model.Role;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;
import org.lilia.logger.Logger;
import org.lilia.logger.LoggerFactory;
import org.lilia.service.dto.PersonDto;
import org.lilia.service.service.CourseService;
import org.lilia.service.service.PersonService;

public class PersonView {

    private static final Logger logger = LoggerFactory.getLogger(PersonView.class);
    private final CourseService courseService;

    public PersonView(CourseService courseService) {
        this.courseService = courseService;
    }

    public void workWithPerson(PersonService personService) {
        logger.info("work with person section");

        String userChoice = "Y";
        while (userChoice.equalsIgnoreCase("Y")) {
            switch (ConsoleUtils.choiceAction()) {
                case 1 -> {
                    logger.info("selected to create person");
                    while (userChoice.equalsIgnoreCase("Y")) {

                        Person person = createNewPerson(personService);
                        logger.info("person created successful " + person.getRole() + " " + person.getLastName());

                        ConsoleUtils.print(Constants.CREATE_NEW);
                        userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
                    }
                }
                case 2 -> {
                    logger.info("selected get person by last name");
                    Person person = getPersonById(personService);

                    ConsoleUtils.print(Constants.ELEMENT_EDIT);
                    userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

                    editPerson(personService, userChoice, person);
                }
                case 3 -> {
                    logger.info("selected output person");
                    outputAll(personService);
                    sortOrMapPersons(personService);
                }
                case 4 -> {
                    logger.info("selected delete person");
                    delete(personService);
                    logger.info("lecture deleted successful");
                }
                case 5 -> {
                    logger.info("selected create backup person");
                    Role role = personService.getRole(selectRole());
                    personService.backupPerson(role);
                }
                case 6 -> {
                    logger.info("selected deserialize person");
                    Role role = personService.getRole(selectRole());
                    personService.deserialize(role);
                }
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

    private Person getPersonById(PersonService personService) {
        ConsoleUtils.print(Constants.LAST_NAME);
        String personLastName = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);
        Person person = personService.getByLastName(personLastName);
        print(person);
        return person;
    }

    private void editPerson(PersonService personService, String userChoice, Person person) {

        while (userChoice.equalsIgnoreCase("Y")) {

            ConsoleUtils.print(Constants.LAST_NAME);
            String personLastName = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);

            ConsoleUtils.print(Constants.NAME);
            String personName = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);

            ConsoleUtils.print(Constants.COURSE_ID);
            String courseId = String.valueOf(courseService.courseIdIsValid());

            ConsoleUtils.print(Constants.PHONE);
            String phone = ConsoleUtils.readAndValidationInput(Constants.NUMBER);

            ConsoleUtils.print(Constants.EMAIL);
            String email = ConsoleUtils.readAndValidationInput(Constants.NUMBER);

            while (!personService.checkEmail(email)) {
                ConsoleUtils.print("This email already registered");

                ConsoleUtils.print(Constants.EMAIL);
                email = ConsoleUtils.readAndValidationInput(Constants.NUMBER);

            }
            String emailChecked = email;

            PersonDto personDto = personService.createPersonDto(personLastName, personName, phone, emailChecked, Integer.parseInt(courseId));

            Person personUpdate = personService.updatePerson(person, personDto);
            System.out.println(personUpdate);

            ConsoleUtils.print(Constants.ELEMENT_EDIT);
            userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);
        }
    }

    private void outputAll(PersonService personService) {
        ConsoleUtils.print(Constants.COURSE_ID);
        int courseId = courseService.courseIdIsValid();

        Role role = personService.getRole(selectRole());

        personService.outAllByCourse(courseId, role);
    }
    private static void sortOrMapPersons(PersonService personService) {
        String userChoice;
        ConsoleUtils.print(Constants.SORT_BY_LAST_NAME_TEACHERS);
        userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

        if (userChoice.equalsIgnoreCase("Y")) {
            personService.sortByLastName();
        }
        ConsoleUtils.print(Constants.PRINT_LAST_NAME_TEACHERS_BEFORE_N);
        userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

        if (userChoice.equalsIgnoreCase("Y")) {
            personService.outputBeforeN();
        }

        ConsoleUtils.print(Constants.CREATE_MAP_PERSONS);
        userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

        if (userChoice.equalsIgnoreCase("Y")) {
            personService.printMap();
        }

        ConsoleUtils.print(Constants.SORT_STUDENTS_BY_EMAIL);
        userChoice = ConsoleUtils.readAndValidationInput(Constants.YES_OR_NO);

        if (userChoice.equalsIgnoreCase("Y")) {
            personService.printEmailsOfStudentsToFile();
        }
    }

    private static Person createNewPerson(PersonService personService) {
        ConsoleUtils.print(Constants.NAME);
        String personName = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);

        Role role = personService.getRole(selectRole());

        return personService.createPerson(personName, role);
    }

    private static int selectRole() {
        ConsoleUtils.print(Constants.ROLE);
        return Integer.parseInt(ConsoleUtils.readAndValidationInput("[1-2]"));
    }

    private static void delete(PersonService personService) {
        ConsoleUtils.print(Constants.LAST_NAME);
        String lastName = personService.lastNameIsValid();
        personService.delete(lastName);
    }

    private void print(Person person) {
        System.out.println(person);
    }
}
