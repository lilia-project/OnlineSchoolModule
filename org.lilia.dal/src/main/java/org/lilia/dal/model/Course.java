package org.lilia.dal.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private static int counter = 0;

    private final Integer id;
    private String name;

    private List<Lecture> list;

    public Course(String name) {
        this.name = name;
        counter++;
        id = counter;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(List<Lecture> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return " course id = " + id +
                " course name = " + name +
                " lectures = " + list;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!Objects.equals(id, course.id)) return false;
        if (!Objects.equals(name, course.name)) return false;
        return Objects.equals(list, course.list);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }

    public static class CourseIDComparator implements Comparator<Course> {

        @Override
        public int compare(Course o1, Course o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}

