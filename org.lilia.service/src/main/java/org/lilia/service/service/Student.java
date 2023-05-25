package org.lilia.service.service;

public class Student implements Runnable {

    private final int numberOfTask;

    public Student(int numberOfTask) {
        this.numberOfTask = numberOfTask;
    }

    @Override
    public void run() {
        System.out.println("I'm working");
    }
}
