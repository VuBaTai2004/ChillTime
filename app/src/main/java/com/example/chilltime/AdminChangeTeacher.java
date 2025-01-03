package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminChangeTeacher extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_change_teacher);
        EdgeToEdge.enable(this);

        String name = getIntent().getStringExtra("teacherName");
        String id = getIntent().getStringExtra("teacherId");
        String phone = getIntent().getStringExtra("teacherPhone");
        String email = getIntent().getStringExtra("teacherEmail");

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        // Danh sách mẫu TeacherProfile
        List<TeacherProfile> teacherList = new ArrayList<>();

        AdminChangeTeacherAdapter adapter = new AdminChangeTeacherAdapter(teacherList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db.collection("teachers").get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        teacherList.clear();
                        for(QueryDocumentSnapshot document : task.getResult()){
                            String name1 = document.getString("name");
                            String id1 = document.getString("id");
                            String phone1 = document.getString("phone");
                            String email1 = document.getString("email");
                            TeacherProfile teacher = new TeacherProfile(name1, id1, phone1, email1);
                            teacherList.add(teacher);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        Button btnModify = findViewById(R.id.teacher_btn_change);
        String classId = getIntent().getStringExtra("classId");
        btnModify.setOnClickListener(v -> {
            // Xử lý sự kiện khi người dùng nhấn nút "Đổi"
            TeacherProfile selectedTeacher = adapter.getSelectedTeacher();
            if (selectedTeacher != null) {
                //
                String selectedName = selectedTeacher.getName();
                String selectedId = selectedTeacher.getId();
                String selectedPhone = selectedTeacher.getPhone();
                String selectedEmail = selectedTeacher.getEmail();
                assert classId != null;
                db.collection("teachers").whereEqualTo("name", name).get()
                                .addOnCompleteListener(task -> {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("teachers").document(document.getId()).collection("class_list").document(classId).delete();
                                    }
                                });
                db.collection("courses_detail").document(classId).update("classTeacher", selectedName);
                db.collection("teachers").whereEqualTo("name", selectedName).get().addOnCompleteListener(task -> {
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> data = new HashMap<>();
                        data.put("classId", classId);
                        db.collection("teachers").document(document.getId()).collection("class_list").document(classId).set(data);
                    }
                });
            }
            Intent intent = new Intent(AdminChangeTeacher.this, AdminOpenClass.class);
            intent.putExtra("classId", classId);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });


    }

}
