package com.example.chilltime;

public class TeacherTranscipt {
    private String studentId;
    private String studentName;
    private String studentProcess;
    private String studentPratice;
    private String studentMidterm;
    private String studentFinal;
    private String studentTotal;
    private String year;
    private String studentClass;

    public TeacherTranscipt(String studentId, String studentName, String studentProcess, String studentPratice, String studentMidterm, String studentFinal, String studentTotal, String year, String studentClass) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentProcess = studentProcess;
        this.studentPratice = studentPratice;
        this.studentMidterm = studentMidterm;
        this.studentFinal = studentFinal;
        this.studentTotal = studentTotal ;
        this.year = year;
        this.studentClass = studentClass;
    }


    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentProcess() {
        return studentProcess;
    }

    public String getStudentPratice() {
        return studentPratice;
    }

    public String getStudentMidterm() {
        return studentMidterm;
    }

    public String getStudentFinal() {
        return studentFinal;
    }

    public String getStudentTotal() {
        return studentTotal;
    }

    public String getYear() {
        return year;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentProcess(String studentProcess) {
        this.studentProcess = studentProcess;
    }

    public void setStudentPratice(String studentPratice) {
        this.studentPratice = studentPratice;
    }

    public void setStudentMidterm(String studentMidterm) {
        this.studentMidterm = studentMidterm;
    }

    public void setStudentFinal(String studentFinal) {
        this.studentFinal = studentFinal;
    }

}
