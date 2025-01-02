package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherExerciseEdit extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.teacher_exercise_edit);

        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");
        String exerciseTime = getIntent().getStringExtra("exerciseTime");
        String exerciseContent = getIntent().getStringExtra("exerciseContent");
        String classId = getIntent().getStringExtra("classId");

        EditText etTitle = findViewById(R.id.et_title);
        EditText etTime = findViewById(R.id.et_time);
        EditText etContent = findViewById(R.id.et_content);

        etTitle.setText(exerciseTitle);
        etTime.setText(exerciseTime);
        etContent.setText(exerciseContent);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        Button editBtn = findViewById(R.id.teacher_btn_add);
        editBtn.setOnClickListener(v -> {
            String updatedTitle = etTitle.getText().toString().trim();
            String updatedTime = etTime.getText().toString().trim();
            String updatedContent = etContent.getText().toString().trim();

            if (classId == null || updatedTitle.isEmpty() || updatedTime.isEmpty() || updatedContent.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("exercises")
                    .whereEqualTo("classId", classId)
                    .whereEqualTo("title", exerciseTitle)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            String documentId = task.getResult().getDocuments().get(0).getId();

                            db.collection("exercises")
                                    .document(documentId)
                                    .update("title", updatedTitle,
                                            "deadline", updatedTime,
                                            "content", updatedContent)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("updatedTitle", updatedTitle);
                                        resultIntent.putExtra("updatedTime", updatedTime);
                                        resultIntent.putExtra("updatedContent", updatedContent);
                                        setResult(RESULT_OK, resultIntent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(this, "Không tìm thấy bài tập cần cập nhật!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
