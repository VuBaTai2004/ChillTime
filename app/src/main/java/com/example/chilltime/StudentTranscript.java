package com.example.chilltime;

public class StudentTranscript {

    private String studentId;
    private String classId;
    private float progressGrade;
    private float practiceGrade;
    private float midtermGrade;
    private float termGrade;
    private float finalGrade;

    public StudentTranscript(String studentId, String classId,  float progressGrade, float practiceGrade, float midtermGrade, float termGrade, float finalGrade) {
        this.studentId = studentId;
        this.classId = classId;
        this.progressGrade = progressGrade;
        this.practiceGrade = practiceGrade;
        this.midtermGrade = midtermGrade;
        this.termGrade = termGrade;
        this.finalGrade = finalGrade;
    }

    public StudentTranscript(String studentId, String classId, float progressGrade, float practiceGrade, float midtermGrade, float termGrade) {
        this.studentId = studentId;
        this.classId = classId;
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


    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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
        float finalGrade = (float) ((progressGrade * 0.15) + (practiceGrade * 0.15) + (midtermGrade * 0.30) + (termGrade * 0.40));

        return finalGrade;
    }

}
