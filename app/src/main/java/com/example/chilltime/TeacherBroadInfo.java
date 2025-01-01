package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class TeacherBroadInfo extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private float progressGrade = 0f;
    private float practiceGrade = 0f;
    private float midtermGrade = 0f;
    private float termGrade = 0f;
    private float finalGrade = 0f;

    private String phone = "";
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_list_info);
        EdgeToEdge.enable(this);

        // Lấy dữ liệu từ Intent
        String name = getIntent().getStringExtra("teacherName");
        String id = getIntent().getStringExtra("teacherId");
        String classId = getIntent().getStringExtra("classId");

        String qt = getIntent().getStringExtra("qt");
        String th = getIntent().getStringExtra("th");
        String gk = getIntent().getStringExtra("gk");
        String ck = getIntent().getStringExtra("ck");
        String tb = getIntent().getStringExtra("tb");

        // Hiển thị dữ liệu lên giao diện
        TextView teacherName = findViewById(R.id.text_name);
        TextView teacherId = findViewById(R.id.text_id);
        TextView teacherPhone = findViewById(R.id.text_phone);
        TextView teacherEmail = findViewById(R.id.text_email);

        TextView textViewProgress = findViewById(R.id.text_progress);
        TextView textViewPractice = findViewById(R.id.text_practice);
        TextView textViewMidterm = findViewById(R.id.text_midterm);
        TextView textViewTerm = findViewById(R.id.text_term);
        TextView textViewFinal = findViewById(R.id.text_final);

        // Lấy thông tin từ Firestore
        fetchTeacherInfo(id, teacherPhone, teacherEmail);

        teacherName.setText("Họ và tên: " + name);
        teacherId.setText("Mã sinh viên: " + id);

        textViewProgress.append(qt);
        textViewPractice.append(th);
        textViewMidterm.append(gk);
        textViewTerm.append(ck);
        textViewFinal.append(tb);

        // Xử lý khi bấm nút back
        ImageView backBtn = findViewById(R.id.back_arrow);
        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        // Xử lý khi bấm nút editBtn
        Button editBtn = findViewById(R.id.teacher_btn_edit);
        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherBroadInfo.this, TeacherListEdit.class);
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

    private void fetchTeacherInfo(String id, TextView teacherPhone, TextView teacherEmail) {
        db.collection("students")
                .whereEqualTo("id", id)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            phone = document.getString("phone");
                            email = document.getString("email");
                        }
                    }
                    // Cập nhật giao diện
                    teacherPhone.setText("Số điện thoại: " + phone);
                    teacherEmail.setText("Email: " + email);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error fetching documents", e);
                });
    }
}
