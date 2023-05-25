package org.lilia.service.service;

import org.lilia.dal.model.Person;
import org.lilia.dal.model.Role;
import org.lilia.dal.repository.PersonRepository;
import org.lilia.logger.exception.NoSuchPersonException;
import org.lilia.logger.serialization.FilePath;
import org.lilia.logger.utils.ConsoleUtils;
import org.lilia.logger.utils.Constants;
import org.lilia.service.dto.PersonDto;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(String lastName, Role role) {
        if (lastName == null | role == null) {
            throw new IllegalArgumentException("last name or role is null");
        }
        Person person = Person.createPerson(lastName, role);
        ConsoleUtils.print(Constants.ELEMENT_CREATED + person);
        personRepository.add(person);
        return person;
    }

    public PersonDto createPersonDto(String lastName, String firstName, String phone, String email, int courseId) {

        return new PersonDto(lastName, firstName, phone, email, courseId);
    }

    public Person updatePerson(Person person, PersonDto personDto) {
        if (personDto.getLastName() != null) {
            person.setLastName(personDto.getLastName());
        }
        if (personDto.getFirstName() != null) {
            person.setFirstName(personDto.getFirstName());
        }
        if (personDto.getPhone() != null) {
            person.setPhone(personDto.getPhone());
        }
        if (personDto.getEmail() != null) {
            person.setEmail(personDto.getEmail());
        }
        if (personDto.getCourseId() != 0) {
            person.setCourseId(personDto.getCourseId());
        }
        return person;
    }

    public Role getRole(int choiceRole) {
        if (choiceRole == 1) {
            return Role.TEACHER;
        } else {
            return Role.STUDENT;
        }
    }

    public void outAllByCourse(int courseId, Role role) {
        personRepository.getByCourseId(courseId, role);
    }

    public void delete(String lastName) {
        Person person = getByLastName(lastName);
        personRepository.remove(person);

    }

    public String lastNameIsValid() {
        String lastName = ConsoleUtils.readAndValidationInput(Constants.NAME_OR_DESCRIPTION);
        Optional<Person> person = personRepository.getByLastName(lastName);
        if (person.isEmpty()) {
            throw new NoSuchPersonException(lastName);

        }
        return person.get().getLastName();
    }

    public Person getByLastName(String lastName) {
        Optional<Person> person = personRepository.getByLastName(lastName);
        if (person.isEmpty()) {
            throw new NoSuchPersonException(lastName);
        }
        return person.get();
    }

    public void sortByLastName() {
        personRepository.sortByLastName();
    }

    public void backupPerson(Role role) {
        personRepository.serializePerson(role);
    }

    public void deserialize(Role role) {
        personRepository.deserializePerson(role);
    }

    public void outputBeforeN() {
        personRepository.printLastNameOfTeachersBeforeN();
    }

    public Boolean checkEmail(String email) {
        return personRepository.checkEmailForDuplicate(email);
    }

    public void printMap() {
        personRepository.printMapKeyEmailValueName();
    }

    public void printEmailsOfStudentsToFile() {
        Path path = Paths.get(FilePath.FILE_PATH_EMAILS_OF_STUDENTS.getPath());
        List<String> emailsOfStudents = personRepository.sortEmailsOfStudents();
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(emailsOfStudents.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

