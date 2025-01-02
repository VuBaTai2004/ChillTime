package com.example.chilltime;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherExerciseWatch extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_exercise_watch);
        EdgeToEdge.enable(this);

        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");
        String exerciseTime = getIntent().getStringExtra("exerciseTime");
        String exerciseContent = getIntent().getStringExtra("exerciseContent");
        String classId = getIntent().getStringExtra("classId");


    }
}
