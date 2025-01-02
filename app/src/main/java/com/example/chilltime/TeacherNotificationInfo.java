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

public class TeacherNotificationInfo extends AppCompatActivity {
    private TextView exerciseTitleTextView;
    private TextView exerciseContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_notification_info);
        EdgeToEdge.enable(this);

        exerciseTitleTextView = findViewById(R.id.text_title);
        exerciseContentTextView = findViewById(R.id.text_content);

        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");
        String exerciseContent = getIntent().getStringExtra("exerciseContent");
        String classId = getIntent().getStringExtra("classId");

        updateUI(exerciseTitle, exerciseContent);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        Button editBtn = findViewById(R.id.teacher_btn_edit);
        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherNotificationInfo.this, TeacherNotificationEdit.class);
            intent.putExtra("exerciseTitle", exerciseTitle);
            intent.putExtra("exerciseContent", exerciseContent);
            intent.putExtra("classId", classId);
            editLauncher.launch(intent); // Bắt đầu Activity với launcher
        });
    }

    private final ActivityResultLauncher<Intent> editLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String updatedTitle = result.getData().getStringExtra("updatedTitle");
                    String updatedContent = result.getData().getStringExtra("updatedContent");

                    // Cập nhật giao diện với dữ liệu mới
                    updateUI(updatedTitle, updatedContent);
                }
            });

    private void updateUI(String title, String content) {
        exerciseTitleTextView.setText("Tiêu đề: " + title);
        exerciseContentTextView.setText("Nội dung: " + content);
    }
}
