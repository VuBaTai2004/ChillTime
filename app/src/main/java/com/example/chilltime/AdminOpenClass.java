package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminOpenClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_open_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Activity activity = new Activity("08:00 - 09:30", "NT532.P11", "B5.06");
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvClass = findViewById(R.id.tvClass);
        TextView tvRoom = findViewById(R.id.tvRoom);

        tvTime.setText("Thời gian: " + activity.getTime());
        tvClass.setText("Lớp: " + activity.getClassName());
        tvRoom.setText("Phòng: " + activity.getRoom());

        String dateTimeString = "2024-11-16 15:30:00";
        Timestamp timestamp = Timestamp.valueOf(dateTimeString);
        TeacherProfile teacherProfile = new TeacherProfile("Pham Minh E", "0868480060", "quanpham0405@gmail.com", timestamp);
        TextView tvTeacherName = findViewById(R.id.teacher_name);

        tvTeacherName.setText(teacherProfile.getName());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<StudentProfile> students = new ArrayList<>();
        AdminOpenClassAdapter adapter = new AdminOpenClassAdapter(this, students);
        recyclerView.setAdapter(adapter);

        students.add(new StudentProfile("Pham Minh Quan", "0868480060", "quanpham0405@gmail.com", timestamp));
        students.add(new StudentProfile("Pham Minh Quan", "0868480060", "quanpham0405@gmail.com", timestamp));


        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(this, AdminAddStudentIntoClass.class);
            startActivity(intent);
        });

        ImageView arrow_icon1 = findViewById(R.id.arrow_icon1);
        arrow_icon1.setOnClickListener(v -> {
            // Handle arrow icon click event
            Intent intent = new Intent(this, AdminTeacherInClass.class);
            intent.putExtra("teacherName", teacherProfile.getName());
            intent.putExtra("teacherPhone", teacherProfile.getPhone());
            intent.putExtra("teacherEmail", teacherProfile.getEmail());
            intent.putExtra("teacherCreatedAt", teacherProfile.getCreatedAt());
            startActivity(intent);
        });

        ConstraintLayout itemTeacher = findViewById(R.id.teacherlayout);
        itemTeacher.setOnClickListener(v -> {
            // Handle arrow icon click event
            Intent intent = new Intent(this, AdminTeacherInClass.class);
            intent.putExtra("teacherName", teacherProfile.getName());
            intent.putExtra("teacherPhone", teacherProfile.getPhone());
            intent.putExtra("teacherEmail", teacherProfile.getEmail());
            intent.putExtra("teacherCreatedAt", teacherProfile.getCreatedAt());
            startActivity(intent);
        });


    }
}
