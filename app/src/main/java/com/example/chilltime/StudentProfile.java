package com.example.chilltime;

import java.sql.Timestamp;

public class StudentProfile {
    private String name;
    private String phone;
    private String email;
    private String id;

    public StudentProfile (String name,String id, String phone, String email) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public String getId() { return id; }
    public String getPhone() {
        return phone;
    }
    public String getEmail(){
        return email;
    }

}
