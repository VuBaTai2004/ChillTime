package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminStudentInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_people_info);
        EdgeToEdge.enable(this);

        TextView studentName = findViewById(R.id.text_name);
        TextView studentId = findViewById(R.id.text_id);
        TextView studentPhone = findViewById(R.id.text_phone);
        TextView studentEmail = findViewById(R.id.text_email);

        String name = getIntent().getStringExtra("studentName");
        String id = getIntent().getStringExtra("studentId");
        String phone = getIntent().getStringExtra("studentPhone");
        String email = getIntent().getStringExtra("studentEmail");

        studentName.setText("Họ và tên: " + name);
        studentId.setText("Mã học viên: " + id);
        studentPhone.setText("Số điện thoại: " + phone);
        studentEmail.setText("Email: " + email);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button btnModify = findViewById(R.id.teacher_btn_edit);
        btnModify.setOnClickListener(v -> {
            // Handle edit button click event
            Intent intent = new Intent(AdminStudentInfo.this, AdminModifyStudent.class);
            intent.putExtra("studentName", name);
            intent.putExtra("studentId", id);
            intent.putExtra("studentPhone", phone);
            intent.putExtra("studentEmail", email);
            startActivity(intent);
        });

        Button btnDelete = findViewById(R.id.teacher_btn_delete);
        btnDelete.setOnClickListener(v -> {
            // Handle delete button click event

        });

        TextView title = findViewById(R.id.title_text);
        title.setText("Học viên");
    }
}
