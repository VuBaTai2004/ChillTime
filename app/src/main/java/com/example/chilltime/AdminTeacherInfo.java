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
        TextView teacherId = findViewById(R.id.text_id);
        TextView teacherPhone = findViewById(R.id.text_phone);
        TextView teacherEmail = findViewById(R.id.text_email);

        String name = getIntent().getStringExtra("teacherName");
        String id = getIntent().getStringExtra("teacherId");
        String phone = getIntent().getStringExtra("teacherPhone");
        String email = getIntent().getStringExtra("teacherEmail");


        teacherName.setText("Họ và tên: " + name);
        teacherId.setText("Mã giảng viên: " + id);
        teacherPhone.setText("Số điện thoại: " + phone);
        teacherEmail.setText("Email: " + email);


        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button btnModify = findViewById(R.id.teacher_btn_edit);
        btnModify.setOnClickListener(v -> {
            // Handle edit button click event
            Intent intent = new Intent(AdminTeacherInfo.this, AdminModifyTeacher.class);
            intent.putExtra("teacherName", name);
            intent.putExtra("teacherId", id);
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
