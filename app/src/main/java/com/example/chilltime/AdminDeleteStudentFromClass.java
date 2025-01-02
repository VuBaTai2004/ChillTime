package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminDeleteStudentFromClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<StudentProfile> students = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_delete_student_from_class);
        EdgeToEdge.enable(this);

        //


        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        RecyclerView recyclerView;
        AdminAddStuIntoClassAdapter adapter;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdminAddStuIntoClassAdapter(students);
        recyclerView.setAdapter(adapter);

        String classId = getIntent().getStringExtra("classId");
        assert classId != null;

        db.collection("courses_detail").document(classId).collection("student_list")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        students.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("students").whereEqualTo("id", document.getId()).get()
                                    .addOnCompleteListener(studentTask -> {
                                        if (studentTask.isSuccessful()) {
                                            for (QueryDocumentSnapshot studentDoc : studentTask.getResult()) {
                                                String id = studentDoc.getString("id");
                                                String name = studentDoc.getString("name");
                                                String phone = studentDoc.getString("phone");
                                                String email = studentDoc.getString("email");
                                                StudentProfile student = new StudentProfile(name, id, phone, email);
                                                students.add(student);
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                        }
                    } else {
                        Log.e("Error", "Không tìm thấy danh sách học sinh.");
                    }
                });


        findViewById(R.id.teacher_btn_delete).setOnClickListener(v -> {
            List<StudentProfile> selectedStudents = adapter.getSelectedStudents();

            for (StudentProfile student : selectedStudents) {
                db.collection("courses_detail").document(classId).collection("student_list").document(student.getId()).delete();
            }
            Intent intent = new Intent(this, AdminOpenClass.class);
            intent.putExtra("classId", classId);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
