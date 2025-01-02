package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AdminOpenClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TeacherProfile teacherProfile;
    ArrayList<StudentProfile> students = new ArrayList<>();
    AdminOpenClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_open_class);

        // UI elements
        ImageView backArrow = findViewById(R.id.back_arrow);
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvClass = findViewById(R.id.tvClass);
        TextView tvRoom = findViewById(R.id.tvRoom);
        TextView tvTeacherName = findViewById(R.id.teacher_name);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton add = findViewById(R.id.add);
        FloatingActionButton delete = findViewById(R.id.delete);
        ImageView arrowIcon1 = findViewById(R.id.arrow_icon1);
        ConstraintLayout itemTeacher = findViewById(R.id.teacherlayout);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminOpenClassAdapter(this, students);
        recyclerView.setAdapter(adapter);

        // Back button
        backArrow.setOnClickListener(v -> onBackPressed());

        // Get data from Intent
        Intent intent = getIntent();
        String classId = intent.getStringExtra("classId");

        // Fetch course details
        db.collection("courses_detail").whereEqualTo("classId", classId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);

                        // Get data from document
                        String time = document.getString("time");
                        String room = document.getString("room");
                        String teacherName = document.getString("classTeacher");

                        // Update UI
                        tvTime.setText("Thời gian: " + time);
                        tvClass.setText("Lớp: " + classId);
                        tvRoom.setText("Phòng: " + room);
                        tvTeacherName.setText(teacherName);

                        // Fetch teacher info
                        fetchTeacherInfo(teacherName);
                    } else {
                        Log.e("Error", "Không tìm thấy thông tin lớp học.");
                    }
                });

        // Fetch student list
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

        // Add button action
        add.setOnClickListener(v -> {
            Intent addIntent = new Intent(this, AdminAddStudentIntoClass.class);
            addIntent.putExtra("classId", classId);
            startActivity(addIntent);
        });

        delete.setOnClickListener(v -> {
            Intent addIntent = new Intent(this, AdminDeleteStudentFromClass.class);
            addIntent.putExtra("classId", classId);
            startActivity(addIntent);
        });

        // Arrow icon click
        arrowIcon1.setOnClickListener(v -> navigateToTeacherProfile());
        itemTeacher.setOnClickListener(v -> navigateToTeacherProfile());
    }

    private void fetchTeacherInfo(String teacherName) {
        db.collection("teachers").whereEqualTo("name", teacherName).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        QueryDocumentSnapshot teacherDoc = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);

                        // Get teacher info
                        String teacherId = teacherDoc.getString("id");
                        String teacherPhone = teacherDoc.getString("phone");
                        String teacherEmail = teacherDoc.getString("email");

                        // Save teacher profile
                        teacherProfile = new TeacherProfile(teacherName, teacherId, teacherPhone, teacherEmail);

                        Log.d("TeacherInfo", "ID: " + teacherId + ", Phone: " + teacherPhone + ", Email: " + teacherEmail);
                    } else {
                        Log.e("Error", "Không tìm thấy thông tin giáo viên.");
                    }
                });
    }

    private void navigateToTeacherProfile() {
        if (teacherProfile != null) {
            Intent intent = new Intent(this, AdminTeacherInClass.class);
            intent.putExtra("teacherName", teacherProfile.getName());
            intent.putExtra("teacherId", teacherProfile.getId());
            intent.putExtra("teacherPhone", teacherProfile.getPhone());
            intent.putExtra("teacherEmail", teacherProfile.getEmail());
            intent.putExtra("classId", getIntent().getStringExtra("classId"));
            startActivity(intent);
        } else {
            Log.e("Error", "Teacher data not loaded yet!");
        }
    }
}
