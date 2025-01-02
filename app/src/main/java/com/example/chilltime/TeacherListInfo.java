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
    private static final int EDIT_REQUEST_CODE = 100; // Mã yêu cầu
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private float progressGrade = 0f;
    private float practiceGrade = 0f;
    private float midtermGrade = 0f;
    private float termGrade = 0f;
    private float finalGrade = 0f;

    private String name, id, phone, email, classId;

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

        // Lấy dữ liệu từ Intent
        name = getIntent().getStringExtra("teacherName");
        id = getIntent().getStringExtra("teacherId");
        phone = getIntent().getStringExtra("teacherPhone");
        email = getIntent().getStringExtra("teacherEmail");
        classId = getIntent().getStringExtra("classId");

        teacherName.setText("Họ và tên: " + name);
        teacherId.setText("Mã sinh viên: " + id);
        teacherPhone.setText("Số điện thoại: " + phone);
        teacherEmail.setText("Email: " + email);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        loadGrades(textProgress, textPractice, textMidterm, textTerm, textFinal);

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

            startActivityForResult(intent, EDIT_REQUEST_CODE); // Gọi Edit Activity
        });
    }

    private void loadGrades(TextView textProgress, TextView textPractice, TextView textMidterm, TextView textTerm, TextView textFinal) {
        db.collection("points")
                .whereEqualTo("classId", classId)
                .whereEqualTo("studentId", id)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        try {
                            progressGrade = Float.parseFloat(document.getString("qt"));
                            practiceGrade = Float.parseFloat(document.getString("th"));
                            midtermGrade = Float.parseFloat(document.getString("gk"));
                            termGrade = Float.parseFloat(document.getString("ck"));

                            finalGrade = (float) ((progressGrade * 0.15) + (practiceGrade * 0.15) + (midtermGrade * 0.30) + (termGrade * 0.40));

                            textProgress.setText("QT: " + progressGrade);
                            textPractice.setText("TH: " + practiceGrade);
                            textMidterm.setText("GK: " + midtermGrade);
                            textTerm.setText("CK: " + termGrade);
                            textFinal.setText("TK: " + String.format("%.2f", finalGrade));
                        } catch (NumberFormatException e) {
                            // Hiển thị lỗi
                            textProgress.setText("Error");
                            textPractice.setText("Error");
                            textMidterm.setText("Error");
                            textTerm.setText("Error");
                            textFinal.setText("Error");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Nhận dữ liệu cập nhật
            progressGrade = data.getFloatExtra("updatedProgress", progressGrade);
            practiceGrade = data.getFloatExtra("updatedPractice", practiceGrade);
            midtermGrade = data.getFloatExtra("updatedMidterm", midtermGrade);
            termGrade = data.getFloatExtra("updatedTerm", termGrade);

            // Cập nhật điểm cuối
            finalGrade = (float) ((progressGrade * 0.15) + (practiceGrade * 0.15) + (midtermGrade * 0.30) + (termGrade * 0.40));

            // Cập nhật giao diện
            TextView textProgress = findViewById(R.id.text_progress);
            TextView textPractice = findViewById(R.id.text_practice);
            TextView textMidterm = findViewById(R.id.text_midterm);
            TextView textTerm = findViewById(R.id.text_term);
            TextView textFinal = findViewById(R.id.text_final);

            textProgress.setText("QT: " + progressGrade);
            textPractice.setText("TH: " + practiceGrade);
            textMidterm.setText("GK: " + midtermGrade);
            textTerm.setText("CK: " + termGrade);
            textFinal.setText("TK: " + String.format("%.2f", finalGrade));
        }
    }
}
