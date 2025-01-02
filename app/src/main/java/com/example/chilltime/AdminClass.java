package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class AdminClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<TeacherClass> classes = new ArrayList<>();
        AdminClassAdapter adapter = new AdminClassAdapter(this, classes);
        recyclerView.setAdapter(adapter);

        Intent intent = this.getIntent();
        String subject = intent.getStringExtra("classId");
        assert subject != null;
        db.collection("courses").whereEqualTo("id", subject).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        classes.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("courses").document(document.getId()).collection("class_list").get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                db.collection("courses_detail").whereEqualTo("classId", document1.getString("id")).get()
                                                        .addOnCompleteListener(task2 -> {
                                                            if (task2.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document2 : task2.getResult()) {
                                                                    TeacherClass teacherClass = new TeacherClass(document2.getString("classId"),document2.getString("classSubject")
                                                                            ,document2.getString("studentNum"), document2.getString("classTeacher"));
                                                                    classes.add(teacherClass);
                                                                }
                                                                adapter.notifyDataSetChanged();
                                                            }
                                                        });
                                            }

                                        }
                                    });
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("err", "Error getting documents.", task.getException());
                    }
                });

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent1 = new Intent(AdminClass.this, AdminAddClass.class);
            intent1.putExtra("subjectId", intent.getStringExtra("classId"));
            intent1.putExtra("classSubject", intent.getStringExtra("classSubject"));
            startActivity(intent1);
        });

        adapter.notifyDataSetChanged();


    }
}
