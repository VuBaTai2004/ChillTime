package com.example.chilltime;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TeacherAddNotification extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_add_notification);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        EditText etTitle = findViewById(R.id.et_title);
        EditText etContent = findViewById(R.id.et_content);

        // Nhận dữ liệu từ Intent
        String classId = getIntent().getStringExtra("classId");
        String classSubject = getIntent().getStringExtra("classSubject");
        String numStu = getIntent().getStringExtra("numStu");

        etTitle.setText(getIntent().getStringExtra("title"));
        etContent.setText(getIntent().getStringExtra("content"));



        Button addBtn = findViewById(R.id.teacher_btn_add);
        addBtn.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            // Kiểm tra các trường không được trống
            if (title.isEmpty()) {
                Toast.makeText(this, "Hãy nhập tiêu đề!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (content.isEmpty()) {
                Toast.makeText(this, "Hãy nhập nội dung!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Thêm dữ liệu vào Firestore
            Map<String, Object> exercise = new HashMap<>();
            exercise.put("title", title);
            exercise.put("content", content);
            exercise.put("classId", classId);

            db.collection("notifications")
                    .add(exercise)
                    .addOnSuccessListener(documentReference -> {
                        Log.d("FirestoreDebug", "Notification added with ID: " + documentReference.getId());
                        Toast.makeText(this, "Thêm thông báo thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Quay lại màn hình trước
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirestoreError", "Error adding notification", e);
                        Toast.makeText(this, "Có lỗi xảy ra khi thêm thông báo!", Toast.LENGTH_SHORT).show();
                    });
        });

    }
}
