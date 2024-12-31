package com.example.chilltime;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AdminAddStudentIntoClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_student_into_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        RecyclerView recyclerView;
        AdminAddStuIntoClassAdapter adapter;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<StudentProfile> studentList = getSampleStudents();

        adapter = new AdminAddStuIntoClassAdapter(studentList);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.teacher_btn_add).setOnClickListener(v -> {
            List<StudentProfile> selectedStudents = adapter.getSelectedStudents();

            for (StudentProfile student : selectedStudents) {
                Log.d("SelectedStudent", "Tên: " + student.getName() +
                        ", Số điện thoại: " + student.getPhone() +
                        ", Email: " + student.getEmail());
            }

        });
    }

    private List<StudentProfile> getSampleStudents() {
        List<StudentProfile> students = new ArrayList<>();

        students.add(new StudentProfile("Nguyen Van A","120", "0123456789", "a@example.com"));
        students.add(new StudentProfile("Tran Thi B","120", "0987654321", "b@example.com"));
        students.add(new StudentProfile("Le Van C","120", "0112233445", "c@example.com"));
        return students;
    }
}

