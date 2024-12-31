package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
        TextView teacherId = findViewById(R.id.text_id);
        TextView teacherPhone = findViewById(R.id.text_phone);
        TextView teacherEmail = findViewById(R.id.text_email);

        String name = getIntent().getStringExtra("teacherName");
        String id = getIntent().getStringExtra("teacherId");
        String phone = getIntent().getStringExtra("teacherPhone");
        String email = getIntent().getStringExtra("teacherEmail");

        teacherName.setText(name);
        teacherId.setText(id);
        teacherPhone.setText(phone);
        teacherEmail.setText(email);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button editBtn = findViewById(R.id.teacher_btn_edit);
        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherListInfo.this, TeacherListEdit.class);
            intent.putExtra("teacherName", name);
            intent.putExtra("teacherId", id);
            intent.putExtra("teacherPhone", phone);
            intent.putExtra("teacherEmail", email);
            startActivity(intent);
        });


    }

}
