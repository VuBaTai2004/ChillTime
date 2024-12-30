package com.example.chilltime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherAddNotification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_add_notification);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        EditText etTitle = findViewById(R.id.et_title);
        EditText etContent = findViewById(R.id.et_content);

        etTitle.setText(getIntent().getStringExtra("title"));
        etContent.setText(getIntent().getStringExtra("content"));

        Button saveBtn = findViewById(R.id.teacher_btn_add);

        saveBtn.setOnClickListener(v -> {

        });

    }
}
