package com.example.chilltime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AdminAddTeacherIntoClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_teacher_into_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        // Danh sách mẫu TeacherProfile
        List<TeacherProfile> teacherList = new ArrayList<>();
        teacherList.add(new TeacherProfile("Phạm Minh Quân","120", "0123456789", "quan@example.com"));
        teacherList.add(new TeacherProfile("Nguyễn Văn A","120", "0987654321", "a@example.com"));
        teacherList.add(new TeacherProfile("Trần Thị B","120", "0356894321", "b@example.com"));

        // Tạo adapter và gán vào RecyclerView
        AdminAddTeacherIntoClassAdapter adapter = new AdminAddTeacherIntoClassAdapter(teacherList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Xử lý sự kiện nút "Thêm"
        Button addButton = findViewById(R.id.teacher_btn_add);
        addButton.setOnClickListener(v -> {
            TeacherProfile selectedTeacher = adapter.getSelectedTeacher();
            if (selectedTeacher != null) {
                Toast.makeText(this, "Đã chọn: " + selectedTeacher.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng chọn giáo viên", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
