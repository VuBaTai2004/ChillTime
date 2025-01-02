package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TeacherOpenExercise extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Exercise> exercises = new ArrayList<>();
    TeacherExerciseAdapter adapter;
    String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.teacher_open_exercise);

        // Lấy dữ liệu từ Intent
        classId = getIntent().getStringExtra("classId");
        String classSubject = getIntent().getStringExtra("classSubject");
        String numStu = getIntent().getStringExtra("numStu");

        // Gán dữ liệu vào TextView (hoặc bất kỳ phần tử UI nào khác)
        TextView classIdTextView = findViewById(R.id.course_code);
        TextView classSubjectTextView = findViewById(R.id.course_title);
        TextView numStuTextView = findViewById(R.id.number_stu);

        classIdTextView.setText(classId);
        classSubjectTextView.setText(classSubject);
        numStuTextView.setText(numStu);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TeacherExerciseAdapter(exercises, this, classId);
        recyclerView.setAdapter(adapter);

        FloatingActionButton add = findViewById(R.id.add1);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherOpenExercise.this, TeacherAddExercise.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExercises(); // Tải lại dữ liệu từ Firestore
    }

    private void loadExercises() {
        db.collection("exercises").whereEqualTo("classId", classId).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        exercises.clear();
                        for(QueryDocumentSnapshot document : task.getResult()){
                            String title = document.getString("title");
                            String time = document.getString("deadline");
                            String message = document.getString("content");
                            exercises.add(new Exercise(title, time, message));
                            Log.d("FirestoreDebug", "Number of documents: " + task.getResult().size());

                        };
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Log.e("FirestoreError", "Error getting documents: ", task.getException());
                    }
                });
    }
}
