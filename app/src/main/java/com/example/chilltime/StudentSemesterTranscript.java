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

    // Getter cho currentSemester
    public String getCurrentSemester() {
        return currentSemester;
    }

    // Getter cho studentTranscripts
    public List<StudentTranscript> getStudentTranscripts() {
        return studentTranscripts;
    }

    // Method để lấy tên học kỳ
    public String getSemesterName() {
        return currentSemester;
    }

    // Method để lấy danh sách các khóa học
    public List<StudentTranscript> getCourses() {
        return studentTranscripts;
    }
}
