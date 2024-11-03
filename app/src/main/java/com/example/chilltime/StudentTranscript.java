package com.example.chilltime;

public class StudentTranscript {

    private String studentId;
    private String semester;;
    private String classId;
    private int credit;
    private float progressGrade;
    private float practiceGrade;
    private float midtermGrade;
    private float termGrade;
    private float finalGrade;

    public StudentTranscript(String studentId, String semester, String classId, int credit, float progressGrade, float practiceGrade, float midtermGrade, float termGrade, float finalGrade) {
        this.studentId = studentId;
        this.semester = semester;
        this.classId = classId;
        this.credit = credit;
        this.progressGrade = progressGrade;
        this.practiceGrade = practiceGrade;
        this.midtermGrade = midtermGrade;
        this.termGrade = termGrade;
        this.finalGrade = finalGrade;
    }

    public StudentTranscript(String studentId, String semester, String classId, int credit, float progressGrade, float practiceGrade, float midtermGrade, float termGrade) {
        this.studentId = studentId;
        this.semester = semester;
        this.classId = classId;
        this.credit = credit;
        this.progressGrade = progressGrade;
        this.practiceGrade = practiceGrade;
        this.midtermGrade = midtermGrade;
        this.termGrade = termGrade;
        this.finalGrade = calculateFinalGrade();
    }

    public String getStudentId(){
        return studentId;
    }

    public void setStudentId(String studentId){
        this.studentId=studentId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public float getProgressGrade() {
        return progressGrade;
    }

    public void setProgressGrade(float progressGrade) {
        this.progressGrade = progressGrade;
    }

    public float getPracticeGrade() {
        return practiceGrade;
    }

    public void setPracticeGrade(float praticeGrade) {
        this.practiceGrade = praticeGrade;
    }

    public float getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(float midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public float getTermGrade() {
        return termGrade;
    }

    public void setTermGrade(float termGrade) {
        this.termGrade = termGrade;
    }

    public float getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(float finalGrade) {
        this.finalGrade = finalGrade;
    }

    public float calculateFinalGrade() {
        return (progressGrade + practiceGrade + midtermGrade + termGrade) / 4;
    }

}
