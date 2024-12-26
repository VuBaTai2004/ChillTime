package com.example.chilltime;

import java.util.List;

public class Subject {
    private String name;
    private List<Notification> notifications;
    private List<Exercise> exercises;

    public Subject(String name, List<Notification> notifications, List<Exercise> exercises) {
        this.name = name;
        this.notifications = notifications;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
