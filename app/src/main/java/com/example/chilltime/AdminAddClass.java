package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminAddClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Button classBtnAdd = findViewById(R.id.teacher_btn_add);
        EditText etId = findViewById(R.id.et_id);
        EditText etSize = findViewById(R.id.et_size);
        EditText etTime = findViewById(R.id.et_time);
        EditText etRoom = findViewById(R.id.et_room);
        String teacherId = getIntent().getStringExtra("teacherId");
        String classId = getIntent().getStringExtra("classId");
        String id = getIntent().getStringExtra("id");
        String studentNum = getIntent().getStringExtra("studentNum");
        String time = getIntent().getStringExtra("time");
        String room = getIntent().getStringExtra("room");
        if(teacherId != null){
            etId.setText(id);
            etSize.setText(studentNum);
            etTime.setText(time);
            etRoom.setText(room);
        }


        classBtnAdd.setOnClickListener(v -> {
            // Handle add button click event
            Map<String, String> classInfo = new HashMap<>();
            classInfo.put("classId", etId.getText().toString());
            classInfo.put("studentNum", etSize.getText().toString());
            classInfo.put("time", etTime.getText().toString());
            classInfo.put("room", etRoom.getText().toString());
            if(teacherId == null){
                Toast.makeText(this, "Vui lòng chọn giảng viên", Toast.LENGTH_SHORT).show();
            }
            else{
                classInfo.put("teacherId", teacherId);
                assert classId != null;
                db.collection("courses").document(classId).collection("class_list").add(classInfo);
            }
        });

        Button classBtnAddTeacher = findViewById(R.id.btn_add_teacher);
        classBtnAddTeacher.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(this, AdminAddTeacherIntoClass.class);
            intent.putExtra("classId", classId);
            intent.putExtra("teacherId", teacherId);
            intent.putExtra("id", etId.getText().toString());
            intent.putExtra("studentNum", etSize.getText().toString());
            intent.putExtra("time", etTime.getText().toString());
            intent.putExtra("room", etRoom.getText().toString());
            startActivity(intent);
        });

    }
}
