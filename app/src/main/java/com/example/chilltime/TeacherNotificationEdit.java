package com.example.chilltime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherNotificationEdit extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.teacher_notification_edit);


        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");
        String exerciseContent = getIntent().getStringExtra("exerciseContent");
        String classId = getIntent().getStringExtra("classId");

        EditText etTitle = findViewById(R.id.et_title);
        EditText etContent = findViewById(R.id.et_content);

        etTitle.setText(exerciseTitle);
        etContent.setText(exerciseContent);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button editBtn = findViewById(R.id.teacher_btn_add);
        editBtn.setOnClickListener(v -> {
            String updatedTitle = etTitle.getText().toString().trim();
            String updatedContent = etContent.getText().toString().trim();

            if (classId == null || updatedTitle.isEmpty() || updatedContent.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tham chiếu đến Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Tìm document cần cập nhật trong collection "exercises"
            db.collection("notification")
                    .whereEqualTo("classId", classId)
                    .whereEqualTo("title", exerciseTitle) // Sử dụng title ban đầu để tìm document
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            String documentId = task.getResult().getDocuments().get(0).getId();

                            // Cập nhật document
                            db.collection("exercises")
                                    .document(documentId)
                                    .update("title", updatedTitle,
                                            "content", updatedContent)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                        finish(); // Quay lại màn hình trước
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(this, "Không tìm thấy bài tập cần cập nhật!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi tìm bài tập: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

}
