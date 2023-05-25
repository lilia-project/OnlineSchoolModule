package org.lilia.service.dto;

public class CourseDto {
    private String name;

    public CourseDto() {
    }

    public CourseDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\n courseName = '" + name + "',";
    }
}
