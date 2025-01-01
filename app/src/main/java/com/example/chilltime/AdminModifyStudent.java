package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AdminModifyStudent extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_modify_teacher);
        EdgeToEdge.enable(this);

        EditText studentName = findViewById(R.id.et_name);
        EditText studentId = findViewById(R.id.et_id);
        EditText studentPhone = findViewById(R.id.et_phone);
        EditText studentEmail = findViewById(R.id.et_email);
        Button btnEdit = findViewById(R.id.teacher_btn_edit);

        String name = getIntent().getStringExtra("studentName");
        String id = getIntent().getStringExtra("studentId");
        String phone = getIntent().getStringExtra("studentPhone");
        String email = getIntent().getStringExtra("studentEmail");

        studentName.setText(name);
        studentId.setText(id);
        studentPhone.setText(phone);
        studentEmail.setText(email);

        // Set up title text
        TextView title = findViewById(R.id.title_text);
        title.setText("Sửa học viên");

        // Set up back arrow
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        btnEdit.setOnClickListener(v -> {
            // Handle edit button click event
            db.collection("students").whereEqualTo("id", id).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                DocumentReference docRef = db.collection("students").document(document.getId());
                                docRef.update("name", studentName.getText().toString());
                                docRef.update("id", studentId.getText().toString());
                                docRef.update("phone", studentPhone.getText().toString());
                                docRef.update("email", studentEmail.getText().toString());
                            }

                            Intent intent = new Intent(this, AdminTeacherInfo.class);
                            intent.putExtra("teacherName", studentName.getText().toString());
                            intent.putExtra("teacherId", studentId.getText().toString());
                            intent.putExtra("teacherPhone", studentPhone.getText().toString());
                            intent.putExtra("teacherEmail", studentEmail.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

        });
    }
}
