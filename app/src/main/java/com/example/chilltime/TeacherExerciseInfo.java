package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherExerciseInfo extends AppCompatActivity {
    private TextView exerciseTitleTextView;
    private TextView exerciseTimeTextView;
    private TextView exerciseContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_exercise_info);
        EdgeToEdge.enable(this);

        exerciseTitleTextView = findViewById(R.id.text_title);
        exerciseTimeTextView = findViewById(R.id.text_time);
        exerciseContentTextView = findViewById(R.id.text_content);

        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");
        String exerciseTime = getIntent().getStringExtra("exerciseTime");
        String exerciseContent = getIntent().getStringExtra("exerciseContent");
        String classId = getIntent().getStringExtra("classId");

        updateUI(exerciseTitle, exerciseTime, exerciseContent);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        Button watchBtn = findViewById(R.id.teacher_btn_watch);
        watchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherExerciseInfo.this, TeacherExerciseWatch.class);
            intent.putExtra("exerciseTitle", exerciseTitle);
            intent.putExtra("exerciseTime", exerciseTime);
            intent.putExtra("exerciseContent", exerciseContent);
            intent.putExtra("classId", classId);
            startActivity(intent);

        });

        Button editBtn = findViewById(R.id.teacher_btn_edit);
        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherExerciseInfo.this, TeacherExerciseEdit.class);
            intent.putExtra("exerciseTitle", exerciseTitle);
            intent.putExtra("exerciseTime", exerciseTime);
            intent.putExtra("exerciseContent", exerciseContent);
            intent.putExtra("classId", classId);
            editLauncher.launch(intent);
        });
    }

    private final ActivityResultLauncher<Intent> editLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String updatedTitle = result.getData().getStringExtra("updatedTitle");
                    String updatedTime = result.getData().getStringExtra("updatedTime");
                    String updatedContent = result.getData().getStringExtra("updatedContent");

                    // Cập nhật giao diện với dữ liệu mới
                    updateUI(updatedTitle, updatedTime, updatedContent);
                }
            });

    private void updateUI(String title, String time, String content) {
        exerciseTitleTextView.setText("Tiêu đề: " + title);
        exerciseTimeTextView.setText("Deadline: " + time);
        exerciseContentTextView.setText("Nội dung: " + content);
    }
}
