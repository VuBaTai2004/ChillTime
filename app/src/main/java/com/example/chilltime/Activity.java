package com.example.chilltime;

public class Activity {
    private String time;
    private String className;
    private String room;

    public Activity(String time, String className, String room) {
        this.time = time;
        this.className = className;
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public String getClassName() {
        return className;
    }

    public String getRoom() {
        return room;
    }
}
