package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminAddStudent extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_teacher);
        EdgeToEdge.enable(this);

        EditText etName = findViewById(R.id.et_name);
        EditText etId = findViewById(R.id.et_id);
        EditText etPhone = findViewById(R.id.et_phone);
        EditText etEmail = findViewById(R.id.et_email);
        EditText etUsername = findViewById(R.id.et_username);

        // Set up title text
        TextView title = findViewById(R.id.title_text);
        title.setText("Thêm học viên");

        // Set up back arrow
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        Button studentBtnAdd = findViewById(R.id.teacher_btn_add);
        studentBtnAdd.setOnClickListener(v -> {
            // Kiểm tra nếu có trường nào trống
            if (etId.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã học viên!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etName.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên học viên!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPhone.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etUsername.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên tài khoản!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Nếu tất cả các trường hợp lệ, thực hiện thêm học viên
            Map<String, String> student = new HashMap<>();
            student.put("id", etId.getText().toString());
            student.put("name", etName.getText().toString());
            student.put("phone", etPhone.getText().toString());
            student.put("email", etEmail.getText().toString());
            student.put("password", PasswordUtil.hashPassword("student123"));
            student.put("username", etUsername.getText().toString());

            db.collection("students").add(student).addOnSuccessListener(documentReference -> {
                Toast.makeText(this, "Thêm học viên thành công!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Có lỗi xảy ra khi thêm học viên: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }
}
