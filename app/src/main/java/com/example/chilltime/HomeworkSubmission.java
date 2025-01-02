package com.example.chilltime;

public class HomeworkSubmission {
    private String studentName;
    private String link;

    public HomeworkSubmission(String studentName, String link) {
        this.studentName = studentName;
        this.link = link;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getLink() {
        return link;
    }
}
