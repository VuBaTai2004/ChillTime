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
        backArrow.setOnClickListener(v -> onBackPressed());

        Button editBtn = findViewById(R.id.teacher_btn_add);
        editBtn.setOnClickListener(v -> {
            String updatedTitle = etTitle.getText().toString().trim();
            String updatedContent = etContent.getText().toString().trim();

            if (classId == null || updatedTitle.isEmpty() || updatedContent.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tìm và cập nhật dữ liệu trong Firestore
            db.collection("notifications")
                    .whereEqualTo("classId", classId)
                    .whereEqualTo("title", exerciseTitle)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            String documentId = task.getResult().getDocuments().get(0).getId();

                            db.collection("notifications")
                                    .document(documentId)
                                    .update("title", updatedTitle, "content", updatedContent)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                                        // Trả dữ liệu phản hồi cho Activity trước đó
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("updatedTitle", updatedTitle);
                                        resultIntent.putExtra("updatedContent", updatedContent);
                                        setResult(RESULT_OK, resultIntent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(this, "Không tìm thấy thông báo cần cập nhật!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
