package com.example.chilltime;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentOpenNotification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_open_notification);
        EdgeToEdge.enable(this);


        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        // Lấy dữ liệu từ Intent
        String classId = getIntent().getStringExtra("classId");
        String classSubject = getIntent().getStringExtra("classSubject");
        String numStu = getIntent().getStringExtra("numStu");
        String classTeacher = getIntent().getStringExtra("classTeacher");

        // Gán dữ liệu vào TextView (hoặc bất kỳ phần tử UI nào khác)
        TextView classIdTextView = findViewById(R.id.course_code);
        TextView classSubjectTextView = findViewById(R.id.course_title);
        TextView numStuTextView = findViewById(R.id.number_stu);
        TextView classTeacherTextView = findViewById(R.id.lecturer_name);

        classIdTextView.setText(classId);
        classSubjectTextView.setText(classSubject);
        numStuTextView.setText(numStu);
        classTeacherTextView.setText(classTeacher);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Notification> notifications = new ArrayList<>();
        TeacherNotificationAdapter adapter = new TeacherNotificationAdapter(notifications, this, classId);
        recyclerView.setAdapter(adapter);

        notifications.add(new Notification("Thông báo 1", "Nội dung thông báo 1"));
        notifications.add(new Notification("Thông báo 2", "Nội dung thông báo 2"));
        notifications.add(new Notification("Thông báo 3", "Nội dung thông báo 3"));

        adapter.notifyDataSetChanged();
    }
}
