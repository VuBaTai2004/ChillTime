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


        classes.add(new TeacherClass("NT131.P13", "Hệ thống Nhúng mạng không dây", "10", "Nguyễn Văn A"));
        classes.add(new TeacherClass("NT131.P14", "Hệ thống Nhúng mạng không dây", "10", "Nguyễn Văn A"));

        Intent intent = this.getIntent();
        String subject = intent.getStringExtra("classId");
        db.collection("courses").document(subject).collection("course_list")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        classes.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            TeacherClass student = new TeacherClass(document.getString("id"), document.getString("subject"),
                                    document.getString("numberStudent").toString(), document.getString("teacher"));
                            Log.d("test", document.getData().toString());
                            classes.add(student);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("err", "Error getting documents.", task.getException());
                    }
                });

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(AdminClass.this, AdminAddClass.class);
            startActivity(intent);
        });

        adapter.notifyDataSetChanged();


    }
}
