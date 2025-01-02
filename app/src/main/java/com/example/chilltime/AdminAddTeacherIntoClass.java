package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AdminAddTeacherIntoClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_teacher_into_class);
        EdgeToEdge.enable(this);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        // Danh sách mẫu TeacherProfile
        List<TeacherProfile> teacherList = new ArrayList<>();

        // Tạo adapter và gán vào RecyclerView
        AdminAddTeacherIntoClassAdapter adapter = new AdminAddTeacherIntoClassAdapter(teacherList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db.collection("teachers").get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        teacherList.clear();
                        for(QueryDocumentSnapshot document : task.getResult()){
                            teacherList.add(new TeacherProfile(document.getString("name").toString(),document.getString("id").toString(),document.getString("phone").toString()
                                    ,document.getString("email").toString()));
                        }
                        adapter.notifyDataSetChanged();
                    }

                });

        // Xử lý sự kiện nút "Thêm"
        Button addButton = findViewById(R.id.teacher_btn_add);
        Intent intent1 = this.getIntent();
        addButton.setOnClickListener(v -> {
            TeacherProfile selectedTeacher = adapter.getSelectedTeacher();
            if (selectedTeacher != null) {
                Intent intent = new Intent(this, AdminAddClass.class);
                intent.putExtra("classId", getIntent().getStringExtra("classId"));
                intent.putExtra("subjectId", getIntent().getStringExtra("subjectId"));
                intent.putExtra("classTeacher", selectedTeacher.getName());
                intent.putExtra("classSubject", getIntent().getStringExtra("classSubject"));
                intent.putExtra("dayOfWeek", getIntent().getStringExtra("dayOfWeek"));
                intent.putExtra("timeStart", getIntent().getStringExtra("timeStart"));
                intent.putExtra("timeEnd", getIntent().getStringExtra("timeEnd"));
                intent.putExtra("studentNum", getIntent().getStringExtra("studentNum"));
                intent.putExtra("time", getIntent().getStringExtra("time"));
                intent.putExtra("room", getIntent().getStringExtra("room"));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng chọn giáo viên", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
