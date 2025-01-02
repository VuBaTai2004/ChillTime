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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AdminStudentInfo extends AppCompatActivity {
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

        String name = getIntent().getStringExtra("studentName");
        String id = getIntent().getStringExtra("studentId");
        String phone = getIntent().getStringExtra("studentPhone");
        String email = getIntent().getStringExtra("studentEmail");

        studentName.setText("Họ và tên: " + name);
        studentId.setText("Mã học viên: " + id);
        studentPhone.setText("Số điện thoại: " + phone);
        studentEmail.setText("Email: " + email);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button btnModify = findViewById(R.id.teacher_btn_edit);
        btnModify.setOnClickListener(v -> {
            // Handle edit button click event
            Intent intent = new Intent(AdminStudentInfo.this, AdminModifyStudent.class);
            intent.putExtra("studentName", name);
            intent.putExtra("studentId", id);
            intent.putExtra("studentPhone", phone);
            intent.putExtra("studentEmail", email);
            startActivity(intent);
        });

        Button btnDelete = findViewById(R.id.teacher_btn_delete);
        btnDelete.setVisibility(View.VISIBLE);
        btnDelete.setOnClickListener(v -> {
            // Handle delete button click event
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa dữ liệu này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Xử lý xóa dữ liệu tại đây
                        db.collection("students").whereEqualTo("id", id).get()
                                .addOnCompleteListener(task -> {
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot document : task.getResult()){
                                            db.collection("students").document(document.getId()).delete();

                                        }
                                        onBackPressed();
                                    }
                                });
                        db.collection("courses_detail").get()
                                .addOnCompleteListener(task -> {
                                   if(task.isSuccessful()){
                                       for(QueryDocumentSnapshot document : task.getResult()){
                                           db.collection("courses_detail").document(document.getId()).collection("student_list").get()
                                                   .addOnCompleteListener(task1 -> {
                                                      for(QueryDocumentSnapshot document1 : task1.getResult()){
                                                          if(document1.getId().equals(id)){
                                                              db.collection("courses_detail").document(document.getId()).collection("student_list").document(document1.getId()).delete();
                                                          }
                                                      }
                                                   });
                                       }
                                   }
                                });
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        // Đóng popup nếu người dùng chọn "Không"
                        dialog.dismiss();
                    })
                    .show();
        });

        TextView title = findViewById(R.id.title_text);
        title.setText("Học viên");
    }
}
