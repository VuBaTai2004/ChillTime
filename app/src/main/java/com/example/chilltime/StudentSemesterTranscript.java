package com.example.chilltime;

import java.util.ArrayList;
import java.util.List;

public class StudentSemesterTranscript {
    private String currentSemester;
    private List<StudentTranscript> studentTranscripts = new ArrayList<>();

    public StudentSemesterTranscript(String currentSemester, List<StudentTranscript> studentTranscripts) {
        this.currentSemester = currentSemester;
        this.studentTranscripts = studentTranscripts;
    }

    public StudentSemesterTranscript(String currentSemester) {
        this.currentSemester = currentSemester;
    }

    public String getCurrentSemester() {
        return currentSemester;
    }

    public List<StudentTranscript> getStudentTranscripts() {
        return studentTranscripts;
    }

}
