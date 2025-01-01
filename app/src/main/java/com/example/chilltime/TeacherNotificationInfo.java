package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherNotificationInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_notification_info);
        EdgeToEdge.enable(this);

        TextView exerciseTitleTextView = findViewById(R.id.text_title);
        TextView exerciseContentTextView = findViewById(R.id.text_content);

        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");
        String exerciseContent = getIntent().getStringExtra("exerciseContent");
        String classId = getIntent().getStringExtra("classId");

        exerciseTitleTextView.setText(exerciseTitle);
        exerciseContentTextView.setText(exerciseContent);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button editBtn = findViewById(R.id.teacher_btn_edit);
        editBtn.setOnClickListener(v -> {
            // Xử lý khi bấm nút editBtn
            Intent intent = new Intent(TeacherNotificationInfo.this, TeacherNotificationEdit.class);
            intent.putExtra("exerciseTitle", exerciseTitle);
            intent.putExtra("exerciseContent", exerciseContent);
            intent.putExtra("classId", classId);
            startActivity(intent);

        });
    }
}
