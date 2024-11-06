package com.example.chilltime;

public class TeacherProfile {
    private String name;
    private String username;
    private String phone;
    private String email;

    public TeacherProfile (String name, String username, String phone, String email) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail(){
        return email;
    }

}


