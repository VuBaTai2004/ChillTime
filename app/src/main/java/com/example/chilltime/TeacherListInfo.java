package com.example.chilltime;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Timestamp;

public class TeacherListInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_list_info);
        EdgeToEdge.enable(this);

        TextView teacherName = findViewById(R.id.text_name);
        TextView teacherPhone = findViewById(R.id.text_phone);
        TextView teacherEmail = findViewById(R.id.text_email);
        TextView teacherCreatedAt = findViewById(R.id.text_created_at);

        String name = getIntent().getStringExtra("teacherName");
        String phone = getIntent().getStringExtra("teacherPhone");
        String email = getIntent().getStringExtra("teacherEmail");
        String createdAt = getIntent().getStringExtra("teacherCreatedAt");

        teacherName.setText(name);
        teacherPhone.setText(phone);
        teacherCreatedAt.setText(createdAt);
        teacherEmail.setText(email);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

    }

}
