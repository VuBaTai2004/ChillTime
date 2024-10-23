package com.example.chilltime;

public class AdminSubject {
    private String className;
    private String classId;

    public AdminSubject(String className, String classId) {
        this.className = className;
        this.classId = classId;
    }

    public String getName() {
        return className;
    }

    public String getClassId() {
        return classId;
    }
}
