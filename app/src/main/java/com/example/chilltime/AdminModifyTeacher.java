package com.example.chilltime;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminModifyTeacher extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_modify_teacher);
        EdgeToEdge.enable(this);

        EditText teacherName = findViewById(R.id.et_name);
        EditText teacherPhone = findViewById(R.id.et_phone);
        EditText teacherEmail = findViewById(R.id.et_email);

        String name = getIntent().getStringExtra("teacherName");
        String phone = getIntent().getStringExtra("teacherPhone");
        String email = getIntent().getStringExtra("teacherEmail");

        teacherName.setText(name);
        teacherPhone.setText(phone);
        teacherEmail.setText(email);

        // Set up title text
        TextView title = findViewById(R.id.title_text);
        title.setText("Sửa giảng viên");

        // Set up back arrow
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}
