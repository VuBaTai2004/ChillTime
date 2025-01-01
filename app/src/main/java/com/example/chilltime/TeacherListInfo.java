package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Timestamp;

public class TeacherListInfo extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private float progressGrade = 0f;
    private float practiceGrade = 0f;
    private float midtermGrade = 0f;
    private float termGrade = 0f;
    private float finalGrade = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_list_info);
        EdgeToEdge.enable(this);

        TextView teacherName = findViewById(R.id.text_name);
        TextView teacherId = findViewById(R.id.text_id);
        TextView teacherPhone = findViewById(R.id.text_phone);
        TextView teacherEmail = findViewById(R.id.text_email);

        TextView textProgress = findViewById(R.id.text_progress);
        TextView textPractice = findViewById(R.id.text_practice);
        TextView textMidterm = findViewById(R.id.text_midterm);
        TextView textTerm = findViewById(R.id.text_term);
        TextView textFinal = findViewById(R.id.text_final);

        String name = getIntent().getStringExtra("teacherName");
        String id = getIntent().getStringExtra("teacherId");
        String phone = getIntent().getStringExtra("teacherPhone");
        String email = getIntent().getStringExtra("teacherEmail");
        String classId = getIntent().getStringExtra("classId");

        teacherName.setText("Họ và tên: " + name);
        teacherId.setText("Mã sinh viên: " + id);
        teacherPhone.setText("Số điện thoại: " + phone);
        teacherEmail.setText("Email: " + email);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        db.collection("points")
                .whereEqualTo("classId", classId)
                .whereEqualTo("studentId", id)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        try {
                            // Lấy các giá trị từ document và chuyển sang số
                            progressGrade = Float.parseFloat(document.getString("qt"));
                            practiceGrade = Float.parseFloat(document.getString("th"));
                            midtermGrade = Float.parseFloat(document.getString("gk"));
                            termGrade = Float.parseFloat(document.getString("ck"));

                            // Tính điểm cuối cùng
                            finalGrade = (float) ((progressGrade * 0.15) + (practiceGrade * 0.15) + (midtermGrade * 0.30) + (termGrade * 0.40));

                            // Gán giá trị vào TextView
                            textProgress.append(String.valueOf(progressGrade));
                            textPractice.append(String.valueOf(practiceGrade));
                            textMidterm.append(String.valueOf(midtermGrade));
                            textTerm.append(String.valueOf(termGrade));
                            textFinal.append(String.format("%.2f", finalGrade)); // Hiển thị 2 chữ số sau dấu phẩy
                        } catch (NumberFormatException e) {
                            textProgress.setText("Error");
                            textPractice.setText("Error");
                            textMidterm.setText("Error");
                            textTerm.setText("Error");
                            textFinal.setText("Error");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi nếu xảy ra
                    textProgress.setText("Error");
                    textPractice.setText("Error");
                    textMidterm.setText("Error");
                    textTerm.setText("Error");
                    textFinal.setText("Error");
                });

        // Xử lý khi bấm nút editBtn
        Button editBtn = findViewById(R.id.teacher_btn_edit);
        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherListInfo.this, TeacherListEdit.class);
            intent.putExtra("teacherName", name);
            intent.putExtra("teacherId", id);
            intent.putExtra("teacherPhone", phone);
            intent.putExtra("teacherEmail", email);
            intent.putExtra("classId", classId);

            // Truyền các điểm vào Intent
            intent.putExtra("progressGrade", progressGrade);
            intent.putExtra("practiceGrade", practiceGrade);
            intent.putExtra("midtermGrade", midtermGrade);
            intent.putExtra("termGrade", termGrade);
            intent.putExtra("finalGrade", finalGrade);

            startActivity(intent);
        });
    }
}
