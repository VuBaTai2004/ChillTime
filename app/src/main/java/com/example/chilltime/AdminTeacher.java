package com.example.chilltime;

public class AdminTeacher {
    private String teacherId;
    private String teacherName;
    private String teacherEmail;
    private String teacherPhone;

    // Constructor, getters, setters, và các phương thức khác
    public AdminTeacher(String teacherId, String teacherName, String teacherEmail, String teacherPhone) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherPhone = teacherPhone;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }
}
