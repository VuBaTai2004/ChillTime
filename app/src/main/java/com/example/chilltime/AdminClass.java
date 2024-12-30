package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminClass extends AppCompatActivity {
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

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(AdminClass.this, AdminAddClass.class);
            startActivity(intent);
        });

        adapter.notifyDataSetChanged();


    }
}
