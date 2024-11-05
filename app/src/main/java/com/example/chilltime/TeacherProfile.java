package com.example.chilltime;

public class TeacherProfile {
    private String name;
    private String username;
    private String password;
    private String phone;
    private String email;

    public TeacherProfile (String name, String username, String password, String phone, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail(){
        return email;
    }

}


