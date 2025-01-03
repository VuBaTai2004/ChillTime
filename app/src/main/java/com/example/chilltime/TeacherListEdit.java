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

import java.util.HashMap;
import java.util.Map;

public class TeacherListEdit extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_list_edit);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        EditText etProgress = findViewById(R.id.et_progress);
        EditText etPractice = findViewById(R.id.et_practice);
        EditText etMidterm = findViewById(R.id.et_midterm);
        EditText etTerm = findViewById(R.id.et_term);

        // Lấy các giá trị từ Intent
        float progress = getIntent().getFloatExtra("progressGrade", 0f);
        float practice = getIntent().getFloatExtra("practiceGrade", 0f);
        float midterm = getIntent().getFloatExtra("midtermGrade", 0f);
        float term = getIntent().getFloatExtra("termGrade", 0f);
        String classId = getIntent().getStringExtra("classId");
        String studentId = getIntent().getStringExtra("teacherId");

        // Hiển thị dữ liệu trong các EditText
        etProgress.setText(String.valueOf(progress));
        etPractice.setText(String.valueOf(practice));
        etMidterm.setText(String.valueOf(midterm));
        etTerm.setText(String.valueOf(term));

        Button saveBtn = findViewById(R.id.teacher_btn_edit);

        saveBtn.setOnClickListener(v -> {
            String updatedProgress = etProgress.getText().toString().trim();
            String updatedPractice = etPractice.getText().toString().trim();
            String updatedMidterm = etMidterm.getText().toString().trim();
            String updatedTerm = etTerm.getText().toString().trim();

            if (updatedProgress.isEmpty() || updatedPractice.isEmpty() || updatedMidterm.isEmpty() || updatedTerm.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                float progressGrade = Float.parseFloat(updatedProgress);
                float practiceGrade = Float.parseFloat(updatedPractice);
                float midtermGrade = Float.parseFloat(updatedMidterm);
                float termGrade = Float.parseFloat(updatedTerm);

                Map<String, Object> updatedData = new HashMap<>();
                updatedData.put("qt", String.valueOf(progressGrade));
                updatedData.put("th", String.valueOf(practiceGrade));
                updatedData.put("gk", String.valueOf(midtermGrade));
                updatedData.put("ck", String.valueOf(termGrade));
                updatedData.put("classId", classId); // Thêm classId vào dữ liệu mới
                updatedData.put("studentId", studentId); // Thêm studentId vào dữ liệu mới

                db.collection("points")
                        .whereEqualTo("classId", classId)
                        .whereEqualTo("studentId", studentId)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // Nếu tìm thấy dữ liệu, cập nhật dữ liệu hiện có
                                String documentId = queryDocumentSnapshots.getDocuments().get(0).getId();
                                db.collection("points").document(documentId)
                                        .update(updatedData)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                                            // Trả kết quả về
                                            Intent resultIntent = new Intent();
                                            resultIntent.putExtra("updatedProgress", progressGrade);
                                            resultIntent.putExtra("updatedPractice", practiceGrade);
                                            resultIntent.putExtra("updatedMidterm", midtermGrade);
                                            resultIntent.putExtra("updatedTerm", termGrade);
                                            setResult(RESULT_OK, resultIntent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                            } else {
                                // Nếu không tìm thấy dữ liệu, thêm mới dữ liệu
                                db.collection("points")
                                        .add(updatedData)
                                        .addOnSuccessListener(documentReference -> {
                                            Toast.makeText(this, "Thêm mới thành công!", Toast.LENGTH_SHORT).show();

                                            // Trả kết quả về
                                            Intent resultIntent = new Intent();
                                            resultIntent.putExtra("updatedProgress", progressGrade);
                                            resultIntent.putExtra("updatedPractice", practiceGrade);
                                            resultIntent.putExtra("updatedMidterm", midtermGrade);
                                            resultIntent.putExtra("updatedTerm", termGrade);
                                            setResult(RESULT_OK, resultIntent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(this, "Thêm mới thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi lấy dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Vui lòng nhập đúng định dạng số!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
