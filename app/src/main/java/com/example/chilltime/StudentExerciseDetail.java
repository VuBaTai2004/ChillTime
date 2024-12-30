package com.example.chilltime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class StudentExerciseDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student_exercise_details);
        // Lấy dữ liệu từ Intent
        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");
        String exerciseTime = getIntent().getStringExtra("exerciseTime");
        String exerciseContent = getIntent().getStringExtra("exerciseContent");
        // Gán dữ liệu vào TextView (hoặc bất kỳ phần tử UI nào khác)

        // Đặt tiêu đề cho Activity
        setTitle(exerciseTitle);

        EditText etTime = findViewById(R.id.et_deadline);
        EditText etContent = findViewById(R.id.et_content);

        etTime.setText(exerciseTime);
        etContent.setText(exerciseContent);

        // Xử lý sự kiện khi người dùng nhấn nút "Nộp bài"
        Button btnSubmit = findViewById(R.id.teacher_btn_add);
        btnSubmit.setOnClickListener(v -> {
            // Thực hiện hành động khi người dùng nhấn nút "Nộp bài"

        });

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });
    }


}
