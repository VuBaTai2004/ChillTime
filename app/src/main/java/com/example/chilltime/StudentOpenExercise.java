package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class StudentOpenExercise extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Exercise> exercises = new ArrayList<>();
    StudentExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_open_exercise);
        EdgeToEdge.enable(this);

        // Cấu hình nút quay lại
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        // Lấy dữ liệu từ Intent
        String classId = getIntent().getStringExtra("classId");
        String classSubject = getIntent().getStringExtra("classSubject");
        String numStu = getIntent().getStringExtra("numStu");
        String classTeacher = getIntent().getStringExtra("classTeacher");
        String username = getIntent().getStringExtra("username");

        // Gán dữ liệu vào TextView (hoặc bất kỳ phần tử UI nào khác)
        TextView classIdTextView = findViewById(R.id.course_code);
        TextView classSubjectTextView = findViewById(R.id.course_title);
        TextView numStuTextView = findViewById(R.id.number_stu);
        TextView classTeacherTextView = findViewById(R.id.lecturer_name);

        classIdTextView.setText(classId);
        classSubjectTextView.setText(classSubject);
        numStuTextView.setText(numStu);
        classTeacherTextView.setText(classTeacher);

        // Cấu hình RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentExerciseAdapter(exercises, this, classId, username);
        recyclerView.setAdapter(adapter);

        // Lấy dữ liệu bài tập từ Firestore
        fetchExercises(classId);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String exerciseTitle = data.getStringExtra("exerciseTitle");

            // Cập nhật lại trạng thái của bài tập trong RecyclerView
            db.collection("exercises")
                    .whereEqualTo("classId", getIntent().getStringExtra("classId"))
                    .whereEqualTo("title", exerciseTitle)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            String exerciseDocId = queryDocumentSnapshots.getDocuments().get(0).getId();

                            db.collection("exercises")
                                    .document(exerciseDocId)
                                    .collection("submit_homework")
                                    .whereEqualTo("username", getIntent().getStringExtra("username"))
                                    .get()
                                    .addOnSuccessListener(submitSnapshots -> {
                                        if (!submitSnapshots.isEmpty()) {
                                            // Đã nộp bài -> Cập nhật màu sắc
                                            for (int i = 0; i < exercises.size(); i++) {
                                                if (exercises.get(i).getTitle().equals(exerciseTitle)) {
                                                    exercises.get(i).setSubmitted(true); // Đặt trạng thái đã nộp
                                                    adapter.notifyItemChanged(i); // Cập nhật chỉ item đó
                                                    break;
                                                }
                                            }
                                        }
                                    });
                        }
                    });
        }
    }

    private void fetchExercises(String classId) {
        db.collection("exercises")
                .whereEqualTo("classId", classId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        exercises.clear(); // Xóa danh sách cũ
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Lấy title và content từ document
                            String title = document.getString("title");
                            String time = document.getString("time");
                            String content = document.getString("content");

                            // Thêm thông báo vào danh sách
                            exercises.add(new Exercise(title, time, content));
                        }

                        // Cập nhật RecyclerView
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("FirestoreError", "Error getting documents: ", task.getException());
                    }
                });
    }
}
