package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AdminModifyTeacher extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_modify_teacher);
        EdgeToEdge.enable(this);

        EditText teacherName = findViewById(R.id.et_name);
        EditText teacherId = findViewById(R.id.et_id);
        EditText teacherPhone = findViewById(R.id.et_phone);
        EditText teacherEmail = findViewById(R.id.et_email);
        Button btnEdit = findViewById(R.id.teacher_btn_edit);

        String name = getIntent().getStringExtra("teacherName");
        String id = getIntent().getStringExtra("teacherId");
        String phone = getIntent().getStringExtra("teacherPhone");
        String email = getIntent().getStringExtra("teacherEmail");

        teacherName.setText(name);
        teacherId.setText(id);
        teacherPhone.setText(phone);
        teacherEmail.setText(email);

        // Set up title text
        TextView title = findViewById(R.id.title_text);
        title.setText("Sửa giảng viên");

        // Set up back arrow
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        btnEdit.setOnClickListener(v -> {
            // Handle edit button click event
            db.collection("teachers").whereEqualTo("id", id).get()
                    .addOnCompleteListener(task -> {
                       if(task.isSuccessful()){
                           for(QueryDocumentSnapshot document : task.getResult()){
                               DocumentReference docRef = db.collection("teachers").document(document.getId());
                               docRef.update("name", teacherName.getText().toString());
                               docRef.update("id", teacherId.getText().toString());
                               docRef.update("phone", teacherPhone.getText().toString());
                               docRef.update("email", teacherEmail.getText().toString());
                           }

                           Intent intent = new Intent(this, AdminTeacherInfo.class);
                           intent.putExtra("teacherName", teacherName.getText().toString());
                           intent.putExtra("teacherId", teacherId.getText().toString());
                           intent.putExtra("teacherPhone", teacherPhone.getText().toString());
                           intent.putExtra("teacherEmail", teacherEmail.getText().toString());
                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intent);
                       }
                    });

        });
    }
}
