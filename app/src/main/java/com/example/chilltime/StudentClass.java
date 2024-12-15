package com.example.chilltime;

public class StudentClass {
    private String classId;
    private String classSubject;
    private String numStu;
    private String classTeacher;

    public String getClassId() {
        return classId;
    }

    public String getClassSubject() {
        return classSubject;
    }

    public String getNumStu() {
        return numStu;
    }
    public String getClassTeacher() {
        return classTeacher;
    }
    public void setNumStu(String numStu) {
        this.numStu = numStu;
    }
    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setClassSubject(String classSubject) {
        this.classSubject = classSubject;
    }

    public StudentClass(String classId, String classSubject, String numStu, String classTeacher) {
        this.classId = classId;
        this.classSubject = classSubject;
        this.numStu = numStu;
        this.classTeacher = classTeacher;
    }
}
