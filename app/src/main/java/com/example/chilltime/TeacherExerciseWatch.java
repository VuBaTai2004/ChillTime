package com.example.chilltime;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherExerciseWatch extends AppCompatActivity {
    private FirebaseFirestore db;
    private ArrayList<String> studentDataList; // Lưu danh sách dữ liệu sinh viên dạng chuỗi
    private TeacherExerciseWatchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_exercise_watch);
        EdgeToEdge.enable(this);

        String classId = getIntent().getStringExtra("classId");
        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");

        db = FirebaseFirestore.getInstance();
        studentDataList = new ArrayList<>(); // Khởi tạo danh sách

        adapter = new TeacherExerciseWatchAdapter(this, studentDataList);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fetchSubmittedStudents(classId, exerciseTitle);
    }

    private void fetchSubmittedStudents(String classId, String exerciseTitle) {
        db.collection("exercises")
                .whereEqualTo("classId", classId)
                .whereEqualTo("title", exerciseTitle)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        String exerciseDocId = queryDocumentSnapshots.getDocuments().get(0).getId();
                        db.collection("exercises")
                                .document(exerciseDocId)
                                .collection("submit_homework")
                                .get()
                                .addOnSuccessListener(submitSnapshots -> {
                                    for (DocumentSnapshot submitDoc : submitSnapshots.getDocuments()) {
                                        String username = submitDoc.getString("username");
                                        String link = submitDoc.getString("link");

                                        if (username != null && link != null) {
                                            fetchStudentDetails(username, link);
                                        }
                                    }
                                })
                                .addOnFailureListener(e -> Log.e("FirestoreError", "Error fetching submissions", e));
                    }
                })
                .addOnFailureListener(e -> Log.e("FirestoreError", "Error fetching exercises", e));
    }

    private void fetchStudentDetails(String username, String link) {
        db.collection("students")
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(studentSnapshots -> {
                    for (DocumentSnapshot studentDoc : studentSnapshots.getDocuments()) {
                        String name = studentDoc.getString("name") != null ? studentDoc.getString("name") : "Unknown Name";
                        String id = studentDoc.getString("id") != null ? studentDoc.getString("id") : "Unknown ID";
                        String phone = studentDoc.getString("phone") != null ? studentDoc.getString("phone") : "Unknown Phone";
                        String email = studentDoc.getString("email") != null ? studentDoc.getString("email") : "Unknown Email";

                        // Thêm chuỗi dữ liệu vào danh sách
                        String studentData = "Name: " + name + ", ID: " + id + ", Phone: " + phone + ", Email: " + email + ", Link: " + link;
                        studentDataList.add(studentData);
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> Log.e("FirestoreError", "Error fetching student details", e));
    }
}
