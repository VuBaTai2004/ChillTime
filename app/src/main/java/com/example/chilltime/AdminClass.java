package com.example.chilltime;

public class AdminClass {
    private String classId;
    private String classSubject;

    public String getClassId() {
        return classId;
    }

    public String getClassSubject() {
        return classSubject;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setClassSubject(String classSubject) {
        this.classSubject = classSubject;
    }

    public AdminClass(String classId, String classSubject) {
        this.classId = classId;
        this.classSubject = classSubject;
    }

    public AdminClass() {
        this.classId = "null";
        this.classSubject = "null";
    }
}
