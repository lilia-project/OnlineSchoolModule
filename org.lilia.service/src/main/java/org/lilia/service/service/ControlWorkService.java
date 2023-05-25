package org.lilia.service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlWorkService {

    private static void fillMapTimesOfWork(Map<Integer, Integer> timesOfWork) {
        int time;
        for (int i = 0; i < 10; i++) {
            time = (int) (Math.random() * 7 + 8);
            timesOfWork.put((i), time);
            System.out.println("Student " + (i + 1) + " did the control work in " + time);
        }
    }

    private static void searchBestTime(Map<Integer, Integer> timesOfWork) {

        int bestTime = 12;
        for (int i = 0; i < timesOfWork.size(); i++) {
            if (timesOfWork.get(i) < bestTime) {
                bestTime = timesOfWork.get(i);
            }
        }
        for (int i = 0; i < timesOfWork.size(); i++) {
            if (timesOfWork.get(i) == bestTime) {
                System.out.println("Student " + (i + 1) + " has the best time = " + timesOfWork.get(i));
            }
        }

    }

    private static void searchWorseTime(Map<Integer, Integer> timesOfWork) {

        for (int i = 0; i < timesOfWork.size(); i++) {
            if (timesOfWork.get(i) > 12) {
                System.out.println("Student " + (i + 1) + " time = " + timesOfWork.get(i) + " couldn't keep");
            }
        }
    }

    private static void distributeTasksToStudent(List<Thread> threads, List<Integer> tasks) {
        int index;
        int max = 9;
        int min = 0;
        for (int i = 1; i < 11; i++) {
            index = (int) (Math.random() * (max - min + 1) + min);
            max--;

            createThreads(threads, tasks, index, i);
            tasks.remove(index);
        }
    }

    private static void createThreads(List<Thread> threads, List<Integer> tasks, int index, int i) {
        Student student = new Student(tasks.get(index));
        Thread thread = new Thread(student);
        threads.add(thread);
        System.out.println("Student " + i + " has task number = " + tasks.get(index));
    }

    private static void fillTasksListRandom(List<Integer> tasks) {
        for (int i = 11; i < 21; i++) {
            tasks.add(i);
        }
        System.out.println("List of tasks\n" + tasks);
    }

    public void startControlWork() {

        int capacity = 10;

        List<Thread> threads = new ArrayList<>(capacity);
        List<Integer> tasks = new ArrayList<>(capacity);
        Map<Integer, Integer> timesOfWork = new HashMap<>(capacity);

        fillTasksListRandom(tasks);
        distributeTasksToStudent(threads, tasks);

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fillMapTimesOfWork(timesOfWork);
        searchBestTime(timesOfWork);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        searchWorseTime(timesOfWork);
    }
}
