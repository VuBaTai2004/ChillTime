package com.example.chilltime;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminAddTeacher extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int collectionSize;

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
        title.setText("Thêm giảng viên");

        // Set up back arrow
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button teacherBtnAdd = findViewById(R.id.teacher_btn_add);
        teacherBtnAdd.setOnClickListener(v -> {
            Log.d("Firestore","So luong tai lieu ben ngoai ham: " + collectionSize);
            // Handle add button click event
            Map<String, String> teacher = new HashMap<>();
            teacher.put("id", etId.getText().toString());
            teacher.put("name", etName.getText().toString());
            teacher.put("phone", etPhone.getText().toString());
            teacher.put("email", etEmail.getText().toString());
            teacher.put("username", etUsername.getText().toString());
            teacher.put("password", PasswordUtil.hashPassword("teacher123"));

            db.collection("teachers").add(teacher);

            onBackPressed();
        });
    }
}
