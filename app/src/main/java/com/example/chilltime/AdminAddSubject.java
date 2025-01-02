package com.example.chilltime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminAddSubject extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_subject);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button subjectBtnAdd = findViewById(R.id.teacher_btn_add);
        EditText etName = findViewById(R.id.et_name);
        EditText etId = findViewById(R.id.et_id);

        subjectBtnAdd.setOnClickListener(v -> {
            // Handle add button click event
            String subjectName = etName.getText().toString();
            String subjectId = etId.getText().toString();

            if (subjectName.isEmpty() || subjectId.isEmpty()) {
                Toast.makeText(this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> subject = new HashMap<>();
            subject.put("id", subjectId);
            subject.put("subject", subjectName);
            db.collection("courses").document(subjectId).set(subject)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(this,"Thêm môn học thành công", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this,"Thêm môn học thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });

        });

    }
}
