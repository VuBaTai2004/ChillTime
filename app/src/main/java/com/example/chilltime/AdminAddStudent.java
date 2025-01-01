package com.example.chilltime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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

        // Set up title text
        TextView title = findViewById(R.id.title_text);
        title.setText("Thêm học viên");

        // Set up back arrow
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button studentBtnAdd = findViewById(R.id.teacher_btn_add);
        studentBtnAdd.setOnClickListener(v -> {
            // Handle add button click event
            // Handle add button click event
            Map<String, String> student = new HashMap<>();
            student.put("id", etId.getText().toString());
            student.put("name", etName.getText().toString());
            student.put("phone", etPhone.getText().toString());
            student.put("email", etEmail.getText().toString());
            student.put("password", "123456");
            student.put("username", "test");

            db.collection("students").add(student);

            onBackPressed();
        });

    }
}
