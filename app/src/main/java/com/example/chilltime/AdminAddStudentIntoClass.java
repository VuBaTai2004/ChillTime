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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminAddStudentIntoClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_student_into_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        RecyclerView recyclerView;
        AdminAddStuIntoClassAdapter adapter;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<StudentProfile> studentList = new ArrayList<>();
        adapter = new AdminAddStuIntoClassAdapter(studentList);
        recyclerView.setAdapter(adapter);

        String classId = getIntent().getStringExtra("classId");
        List<String> students = new ArrayList<>();
        assert classId != null;
        db.collection("courses_detail").document(classId).collection("student_list").get()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       studentList.clear();
                        for(QueryDocumentSnapshot document : task.getResult()){
                            students.add(document.getId());
                        }
                        db.collection("students").orderBy("id").get().addOnCompleteListener(studentTask -> {
                            for(QueryDocumentSnapshot studentDoc : studentTask.getResult()) {
                                boolean isFound = false;
                                for (String studentId : students) {
                                    if (Objects.equals(studentDoc.getString("id"), studentId)) {
                                        isFound = true;
                                        break;
                                    }
                                }
                                if (!isFound) {
                                    String id = studentDoc.getString("id");
                                    String name = studentDoc.getString("name");
                                    String phone = studentDoc.getString("phone");
                                    String email = studentDoc.getString("email");
                                    StudentProfile student = new StudentProfile(name, id, phone, email);
                                    studentList.add(student);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        });
                   }
                });


        findViewById(R.id.teacher_btn_add).setOnClickListener(v -> {
            List<StudentProfile> selectedStudents = adapter.getSelectedStudents();

            for (StudentProfile student : selectedStudents) {
                db.collection("courses_detail").document(classId).collection("student_list").document(student.getId()).set(student);
            }
            Intent intent = new Intent(this, AdminOpenClass.class);
            intent.putExtra("classId", classId);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

}

