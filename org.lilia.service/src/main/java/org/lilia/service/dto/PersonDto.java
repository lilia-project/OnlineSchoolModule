package org.lilia.service.dto;

public class PersonDto {
    private String lastName;
    private String firstName;
    private String phone;
    private String email;
    private int courseId;

    public PersonDto() {
    }

    public PersonDto(String lastName, String firstName, String phone, String email, int courseId) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.email = email;
        this.courseId = courseId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "\n lastName = '" + lastName + "'," +
                "\n firstName = '" + firstName + "'," +
                "\n phone = '" + phone + "'" +
                "\n email = '" + email + "'" +
                "\n courseId = " + courseId;
    }
}
