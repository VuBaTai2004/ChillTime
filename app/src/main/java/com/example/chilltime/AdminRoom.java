package com.example.chilltime;

public class AdminRoom {
    private String roomId;
    private String classId;

    public AdminRoom(String roomId, String classId) {
        this.roomId = roomId;
        this.classId = classId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getClassId() {
        return classId;
    }
}
