package org.lilia.service.dto;

public class HomeworkDto {
    private String task;

    public HomeworkDto(String name) {
        this.task = name;
    }

    public HomeworkDto() {
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "\n homeworkName = '" + task + "',";
    }
}
