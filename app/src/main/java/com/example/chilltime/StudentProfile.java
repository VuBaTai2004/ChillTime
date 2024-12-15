package com.example.chilltime;

import java.sql.Timestamp;

public class StudentProfile {
    private String name;
    private Timestamp created_at;
    private String phone;
    private String email;

    public StudentProfile (String name, String phone, String email, Timestamp created_at) {
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
    public Timestamp getCreatedAt() {
        return created_at;
    }

}
