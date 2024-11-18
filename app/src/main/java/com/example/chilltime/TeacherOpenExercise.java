package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherOpenExercise extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.teacher_open_exercise);

        // Lấy dữ liệu từ Intent
        String classId = getIntent().getStringExtra("classId");
        String classSubject = getIntent().getStringExtra("classSubject");
        String numStu = getIntent().getStringExtra("numStu");

        // Gán dữ liệu vào TextView (hoặc bất kỳ phần tử UI nào khác)
        TextView classIdTextView = findViewById(R.id.course_code);
        TextView classSubjectTextView = findViewById(R.id.course_title);
        TextView numStuTextView = findViewById(R.id.number_stu);

        classIdTextView.setText(classId);
        classSubjectTextView.setText(classSubject);
        numStuTextView.setText(numStu);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Exercise> exercises = new ArrayList<>();
        TeacherExerciseAdapter adapter = new TeacherExerciseAdapter(exercises, this);
        recyclerView.setAdapter(adapter);

        exercises.add(new Exercise("Bài tập 1", "10:00 - 12:00", "Nội dung bài tập 1"));
        exercises.add(new Exercise("Bài tập 2", "13:00 - 15:00", "Nội dung bài tập 2"));
        exercises.add(new Exercise("Bài tập 3", "16:00 - 18:00", "Nội dung bài tập 3"));

        ImageView add = findViewById(R.id.add1);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherOpenExercise.this, TeacherAddExercise.class);
            startActivity(intent);
        });


        adapter.notifyDataSetChanged();


    }
}
