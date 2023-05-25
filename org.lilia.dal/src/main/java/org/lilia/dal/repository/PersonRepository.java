package org.lilia.dal.repository;

import org.lilia.dal.model.Person;
import org.lilia.dal.model.Role;
import org.lilia.logger.serialization.FilePath;
import org.lilia.logger.serialization.Serializer;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class PersonRepository {
    private final List<Person> personList = new ArrayList<>();

    private static FilePath getPath(Role role) {

        FilePath filePath;
        if (role.getField().equals("TEACHER")) {
            filePath = FilePath.FILE_PATH_TEACHER;
        } else {
            filePath = FilePath.FILE_PATH_STUDENT;
        }
        return filePath;
    }

    private static List<Person> splitListOfPerson(List<Person> data, Role role) {
        List<Person> splitList = data.stream()
                .filter(person -> person.getRole().equals(role))
                .toList();

        return Optional.of(splitList).orElse(Collections.emptyList());
    }

    public void add(Person person) {
        personList.add(person);
    }

    public void remove(Person person) {
        personList.remove(person);
    }

    public Optional<Person> getById(int id) {

        for (Person person : personList) {
            if (person.getId() == id) {
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }

    public void getByCourseId(int courseId, Role role) {
        for (Person person : personList) {
            if (person.getCourseId() == courseId & person.getRole().equals(role)) {
                System.out.println(person);
            }
        }
    }

    public Optional<Person> getByLastName(String personLastName) {
        for (Person person : personList) {
            if (person.getLastName().equals(personLastName)) {
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }

    public void sortByLastName() {
        personList.stream()
                .map(Person::getLastName)
                .sorted()
                .forEach(System.out::println);
    }

    public void serializePerson(Role role) {
        FilePath filePath = getPath(role);
        List<Person> newList = splitListOfPerson(personList, role);
        Serializer.serialize(newList, filePath);
        ConsoleUtils.print(Constants.SERIALIZATION_COMPLETED);
    }

    public void deserializePerson(Role role) {
        FilePath filePath = getPath(role);
        Object deserialize = Serializer.deserialize(filePath.getPath());
        List<Person> list = (List<Person>) deserialize;
        ConsoleUtils.print(Constants.DESERIALIZATION_COMPLETED);

        personList.addAll(list);
    }

    public void printLastNameOfTeachersBeforeN() {
        personList.stream()
                .map(Person::getLastName)
                .filter(it -> (it.substring(0, 1)).compareToIgnoreCase("N") <= 0)
                .forEach(System.out::println);
    }

    public Boolean checkEmailForDuplicate(String email) {
        return personList.stream()
                .map(Person::getEmail)
                .filter(Objects::nonNull)
                .noneMatch(it -> it.equals(email));
    }

    public void printMapKeyEmailValueName() {
        Map<String, String> collect = personList.stream()
                .collect(Collectors.toMap(Person::getEmail, i -> (i.getLastName() + " " + i.getFirstName())));
        System.out.println(collect);
    }

    public List<String> sortEmailsOfStudents() {
        List<Person> students = splitListOfPerson(personList, Role.STUDENT);
        List<String> emailsOfStudents = students.stream()
                .map(Person::getEmail)
                .sorted()
                .toList();
        return Optional.of(emailsOfStudents).orElse(Collections.emptyList());
    }
}

