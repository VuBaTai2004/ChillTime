package com.example.chilltime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminAddTeacher extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_teacher);
        EdgeToEdge.enable(this);

        // Set up title text
        TextView title = findViewById(R.id.title_text);
        title.setText("Thêm giảng viên");

        // Set up back arrow
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button teacherBtnAdd = findViewById(R.id.teacher_btn_add);
        teacherBtnAdd.setOnClickListener(v -> {
            // Handle add button click event
        });

    }
}
