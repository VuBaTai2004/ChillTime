package com.example.chilltime;

public class AdminSubject {
    private String subjectId;
    private String subjectName;

    public AdminSubject(String subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getName() {
        return subjectName;
    }
}
