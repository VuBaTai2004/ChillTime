package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminOpenClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_open_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Activity activity = new Activity("08:00 - 09:30", "NT532.P11", "B5.06");
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvClass = findViewById(R.id.tvClass);
        TextView tvRoom = findViewById(R.id.tvRoom);

        tvTime.setText("Thời gian: " + activity.getTime());
        tvClass.setText("Lớp: " + activity.getClassName());
        tvRoom.setText("Phòng: " + activity.getRoom());

        TeacherProfile teacherProfile = new TeacherProfile("Pham Minh E","120", "0868480060", "quanpham0405@gmail.com");
        TextView tvTeacherName = findViewById(R.id.teacher_name);

        tvTeacherName.setText(teacherProfile.getName());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<StudentProfile> students = new ArrayList<>();
        AdminOpenClassAdapter adapter = new AdminOpenClassAdapter(this, students);
        recyclerView.setAdapter(adapter);

        Intent in = this.getIntent();
        String classId = in.getStringExtra("classId");
        String classSubject = in.getStringExtra("classSubject");
        String numStu = in.getStringExtra("numStu");
        String classTeacher = in.getStringExtra("classTeacher");

        db.collection("courses_detail").document(classId).collection("student_list")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        students.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("students").whereEqualTo("id", document.getId()).get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            String name, phone, email, id;
                                            for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                id = document1.getString("id");
                                                name = document1.getString("name");
                                                phone = document1.getString("phone");
                                                email = document1.getString("email");
                                                StudentProfile student = new StudentProfile(name, id, phone,
                                                        email);
                                                students.add(student);
                                                Log.d("test", document1.getData().toString());
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("err", "Error getting documents.", task.getException());
                    }
                });
        students.add(new StudentProfile("Pham Minh Quan","120", "0868480060", "quanpham0405@gmail.com"));
        students.add(new StudentProfile("Pham Minh Quan","120", "0868480060", "quanpham0405@gmail.com"));


        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(this, AdminAddStudentIntoClass.class);
            startActivity(intent);
        });

        ImageView arrow_icon1 = findViewById(R.id.arrow_icon1);
        arrow_icon1.setOnClickListener(v -> {
            // Handle arrow icon click event
            Intent intent = new Intent(this, AdminTeacherInClass.class);
            intent.putExtra("teacherName", teacherProfile.getName());
            intent.putExtra("teacherId", teacherProfile.getId());
            intent.putExtra("teacherPhone", teacherProfile.getPhone());
            intent.putExtra("teacherEmail", teacherProfile.getEmail());
            startActivity(intent);
        });

        ConstraintLayout itemTeacher = findViewById(R.id.teacherlayout);
        itemTeacher.setOnClickListener(v -> {
            // Handle arrow icon click event
            Intent intent = new Intent(this, AdminTeacherInClass.class);
            intent.putExtra("teacherName", teacherProfile.getName());
            intent.putExtra("teacherId", teacherProfile.getId());
            intent.putExtra("teacherPhone", teacherProfile.getPhone());
            intent.putExtra("teacherEmail", teacherProfile.getEmail());
            startActivity(intent);
        });


    }
}
