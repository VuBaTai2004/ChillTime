package com.example.chilltime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherListEdit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_list_edit);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        EditText etProgress = findViewById(R.id.et_progress);
        EditText etPractice = findViewById(R.id.et_practice);
        EditText etMidterm = findViewById(R.id.et_midterm);
        EditText etTerm = findViewById(R.id.et_term);

        String progress = getIntent().getStringExtra("progress");
        String practice = getIntent().getStringExtra("practice");
        String midterm = getIntent().getStringExtra("midterm");
        String term = getIntent().getStringExtra("term");

        etProgress.setText(progress);
        etPractice.setText(practice);
        etMidterm.setText(midterm);
        etTerm.setText(term);

        Button saveBtn = findViewById(R.id.teacher_btn_edit);

        saveBtn.setOnClickListener(v -> {

        });

    }
}
