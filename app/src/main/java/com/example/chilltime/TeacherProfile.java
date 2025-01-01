package com.example.chilltime;

import java.sql.Timestamp;

public class TeacherProfile {
    private String name;
    private String id;
    private String phone;
    private String email;

    public TeacherProfile (String name,String id, String phone, String email) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


