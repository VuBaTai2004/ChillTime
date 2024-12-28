package com.example.chilltime;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TeacherOpenList extends AppCompatActivity {
    private TeacherProfile teacherProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_open_list);
        EdgeToEdge.enable(this);

        // Lấy dữ liệu từ Intent
        String classId = getIntent().getStringExtra("classId");
        String classSubject = getIntent().getStringExtra("classSubject");
        String numStu = getIntent().getStringExtra("numStu");

        // Gán dữ liệu vào TextView (hoặc bất kỳ phần tử UI nào khác)
        TextView classIdTextView = findViewById(R.id.course_code);
        TextView classSubjectTextView = findViewById(R.id.course_title);
        TextView numStuTextView = findViewById(R.id.number_stu);

        classIdTextView.setText(classId);
        classSubjectTextView.setText(classSubject);
        numStuTextView.setText(numStu);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });


        RecyclerView recyclerView = findViewById(R.id.rv_class_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<TeacherProfile> students = new ArrayList<>();
        TeacherListAdapter adapter = new TeacherListAdapter(this, students);
        recyclerView.setAdapter(adapter);

        String dateTimeString = "2024-11-16 15:30:00";
        Timestamp timestamp = Timestamp.valueOf(dateTimeString);
        students.add(new TeacherProfile("Pham Minh E", "0868480060", "quanpham0405@gmail.com", timestamp));

        String dateTimeString1 = "2024-11-16 15:30:00";
        Timestamp timestamp1 = Timestamp.valueOf(dateTimeString1);
        students.add(new TeacherProfile("Pham Minh E", "0868480060", "quanpham0405@gmail.com", timestamp1));

        String dateTimeString2 = "2024-11-16 15:30:00";
        Timestamp timestamp2 = Timestamp.valueOf(dateTimeString2);
        students.add(new TeacherProfile("Pham Minh E", "0868480060", "quanpham0405@gmail.com", timestamp2));


        adapter.notifyDataSetChanged();


    }
}
