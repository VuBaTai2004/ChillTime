package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminTeacherInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_people_info);
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

        Button btnModify = findViewById(R.id.teacher_btn_edit);
        btnModify.setOnClickListener(v -> {
            // Handle edit button click event
            Intent intent = new Intent(AdminTeacherInfo.this, AdminModifyTeacher.class);
            intent.putExtra("teacherName", name);
            intent.putExtra("teacherPhone", phone);
            intent.putExtra("teacherEmail", email);
            startActivity(intent);
        });

        Button btnDelete = findViewById(R.id.teacher_btn_delete);
        btnDelete.setOnClickListener(v -> {
            // Handle delete button click event

        });
        TextView title = findViewById(R.id.title_text);
        title.setText("Giảng viên");
    }
}
