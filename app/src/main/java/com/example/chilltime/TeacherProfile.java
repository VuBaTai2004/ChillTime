package com.example.chilltime;

import java.sql.Timestamp;

public class TeacherProfile {
    private String name;
    private Timestamp created_at;
    private String phone;
    private String email;

    public TeacherProfile (String name, String phone, String email, Timestamp created_at) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail(){
        return email;
    }
    public Timestamp getCreated_at() {
        return created_at;
    }

}


