package com.example.chilltime;

public class AdminStudent {
    private String studentId;
    private String studentName;
    private String studentEmail;
    private String studentPhone;

    public AdminStudent(String studentId, String studentName, String studentEmail, String studentPhone){
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
    }
    public String getStudentId() {
        return studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }
}
