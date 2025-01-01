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

public class TeacherAddExercise extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_add_exercise);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        EditText etTitle = findViewById(R.id.et_title);
        EditText etTime = findViewById(R.id.et_deadline);
        EditText etContent = findViewById(R.id.et_content);

        // Nhận dữ liệu từ Intent
        String classId = getIntent().getStringExtra("classId");
        String classSubject = getIntent().getStringExtra("classSubject");
        String numStu = getIntent().getStringExtra("numStu");

        Button addBtn = findViewById(R.id.teacher_btn_add);
        addBtn.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            // Kiểm tra các trường không được trống
            if (title.isEmpty()) {
                Toast.makeText(this, "Hãy nhập tiêu đề!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (time.isEmpty()) {
                Toast.makeText(this, "Hãy nhập thời hạn!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (content.isEmpty()) {
                Toast.makeText(this, "Hãy nhập nội dung!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Thêm dữ liệu vào Firestore
            Map<String, Object> exercise = new HashMap<>();
            exercise.put("title", title);
            exercise.put("deadline", time);
            exercise.put("content", content);
            exercise.put("classId", classId);

            db.collection("exercises")
                    .add(exercise)
                    .addOnSuccessListener(documentReference -> {
                        Log.d("FirestoreDebug", "Exercise added with ID: " + documentReference.getId());
                        Toast.makeText(this, "Thêm bài tập thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Quay lại màn hình trước
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirestoreError", "Error adding exercise", e);
                        Toast.makeText(this, "Có lỗi xảy ra khi thêm bài tập!", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
