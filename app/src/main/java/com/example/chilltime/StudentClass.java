package com.example.chilltime;

public class StudentClass {
    private String classId;
    private String className;
    private String startTime;
    private String endTime;

    // Constructor
    public StudentClass(String classId, String className, String startTime, String endTime) {
        this.classId = classId;
        this.className = className;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public String getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    // Setters
    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
