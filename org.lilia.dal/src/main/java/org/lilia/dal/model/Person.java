package org.lilia.dal.model;

import java.io.Serializable;

public class Person implements Serializable {
    private static int counter = 0;
    private final Integer id;
    private final Role role;

    private String lastName;

    private String firstName;

    private String phone;
    private String email;
    private int courseId;
    private int lectureId;

    public Person(String lastName, Role role) {
        this.lastName = lastName;
        this.role = role;
        counter++;
        id = counter;
    }

    public static Person createPerson(String lastName, Role role) {
        return new Person(lastName, role);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getLectureId() {
        return lectureId;
    }

    @Override
    public String toString() {
        return "\n Person{" +
                "\n id = " + id +
                "\n Last name = " + lastName +
                "\n First name = " + firstName +
                "\n courseId = " + courseId +
                "\n lectureId = " + lectureId +
                "\n Phone = " + phone +
                "\n Email = " + email +
                "\n }";
    }

}
