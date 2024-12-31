package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminAddClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button classBtnAdd = findViewById(R.id.teacher_btn_add);
        classBtnAdd.setOnClickListener(v -> {
            // Handle add button click event
        });

        Button classBtnAddTeacher = findViewById(R.id.btn_add_teacher);
        classBtnAddTeacher.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(this, AdminAddTeacherIntoClass.class);
            startActivity(intent);
        });

    }
}
