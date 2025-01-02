package com.example.chilltime;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminChangeTeacher extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_change_teacher);
        EdgeToEdge.enable(this);

        String name = getIntent().getStringExtra("teacherName");
        String id = getIntent().getStringExtra("teacherId");
        String phone = getIntent().getStringExtra("teacherPhone");
        String email = getIntent().getStringExtra("teacherEmail");

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        // Danh sách mẫu TeacherProfile
        List<TeacherProfile> teacherList = new ArrayList<>();
        teacherList.add(new TeacherProfile("Phạm Minh Quân","120", "0123456789", "quan@example.com"));
        teacherList.add(new TeacherProfile("Nguyễn Văn A","120", "0987654321", "a@example.com"));
        teacherList.add(new TeacherProfile("Trần Thị B","120", "0356894321", "b@example.com"));


        AdminChangeTeacherAdapter adapter = new AdminChangeTeacherAdapter(teacherList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





    }

}
