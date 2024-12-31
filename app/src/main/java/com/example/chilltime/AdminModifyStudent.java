package com.example.chilltime;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminModifyStudent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_modify_teacher);
        EdgeToEdge.enable(this);

        EditText studentName = findViewById(R.id.et_name);
        EditText studentId = findViewById(R.id.et_id);
        EditText studentPhone = findViewById(R.id.et_phone);
        EditText studentEmail = findViewById(R.id.et_email);

        String name = getIntent().getStringExtra("studentName");
        String id = getIntent().getStringExtra("studentId");
        String phone = getIntent().getStringExtra("studentPhone");
        String email = getIntent().getStringExtra("studentEmail");

        studentName.setText(name);
        studentId.setText(id);
        studentPhone.setText(phone);
        studentEmail.setText(email);

        // Set up title text
        TextView title = findViewById(R.id.title_text);
        title.setText("Sửa học viên");

        // Set up back arrow
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}
