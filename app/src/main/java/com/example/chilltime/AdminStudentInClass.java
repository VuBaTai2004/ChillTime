package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AdminStudentInClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_people_info);
        EdgeToEdge.enable(this);

        TextView studentName = findViewById(R.id.text_name);
        TextView studentId = findViewById(R.id.text_id);
        TextView studentPhone = findViewById(R.id.text_phone);
        TextView studentEmail = findViewById(R.id.text_email);

        String name = "Họ và tên: " + getIntent().getStringExtra("studentName");
        String id = "Mã học viên: " + getIntent().getStringExtra("studentId");
        String phone = "Số điện thoại: " + getIntent().getStringExtra("studentPhone");
        String email = "Gmail: " + getIntent().getStringExtra("studentEmail");
        String classId = getIntent().getStringExtra("classId");
        String classSubject = getIntent().getStringExtra("classSubject");
        String numStu = getIntent().getStringExtra("numStu");
        String classTeacher = getIntent().getStringExtra("classTeacher");

        studentName.setText(name);
        studentId.setText(id);
        studentPhone.setText(phone);
        studentEmail.setText(email);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button btnDelete = findViewById(R.id.teacher_btn_delete);
        btnDelete.setOnClickListener(v -> {
            // Handle delete button click event
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa dữ liệu này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Xử lý xóa dữ liệu tại đây
                        db.collection("courses_detail").document(classId).collection("student_list").document(getIntent().getStringExtra("studentId")).delete();

                        Intent intent = new Intent(this, AdminOpenClass.class);

                        intent.putExtra("classId", classId);
                        intent.putExtra("classSubject", classSubject);
                        intent.putExtra("numStu", numStu);
                        intent.putExtra("classTeacher", classTeacher);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        this.startActivity(intent);
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        // Đóng popup nếu người dùng chọn "Không"
                        dialog.dismiss();
                    })
                    .show();
        });

        Button btnEdit = findViewById(R.id.teacher_btn_edit);
        btnEdit.setVisibility(View.INVISIBLE);
        TextView title = findViewById(R.id.title_text);
        title.setText("Học viên");
    }
}
