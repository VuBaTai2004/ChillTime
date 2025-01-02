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
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
        backArrow.setOnClickListener(v -> onBackPressed());

        Button classBtnAdd = findViewById(R.id.teacher_btn_add);
        EditText etId = findViewById(R.id.et_id);
        EditText etSize = findViewById(R.id.et_size);
        EditText etDayOfWeek = findViewById(R.id.et_day_of_week);
        EditText etDayStart = findViewById(R.id.et_day_start);
        EditText etDayEnd = findViewById(R.id.et_day_end);
        EditText etTime = findViewById(R.id.et_time);
        EditText etRoom = findViewById(R.id.et_room);

        String classTeacher = getIntent().getStringExtra("classTeacher");
        String subjectId = getIntent().getStringExtra("subjectId");
        String classId = getIntent().getStringExtra("classId");
        String studentNum = getIntent().getStringExtra("studentNum");
        String time = getIntent().getStringExtra("time");
        String room = getIntent().getStringExtra("room");
        String classSubject = getIntent().getStringExtra("classSubject");
        String dayOfWeek = getIntent().getStringExtra("dayOfWeek");
        String timeStart = getIntent().getStringExtra("timeStart");
        String timeEnd = getIntent().getStringExtra("timeEnd");

        if (classTeacher != null) {
            etId.setText(classId);
            etDayOfWeek.setText(dayOfWeek);
            etDayStart.setText(timeStart);
            etDayEnd.setText(timeEnd);
            etSize.setText(studentNum);
            etTime.setText(time);
            etRoom.setText(room);
        }

        classBtnAdd.setOnClickListener(v -> {
            // Kiểm tra nếu có trường EditText nào trống
            if (etId.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã lớp!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etSize.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số lượng học viên!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etDayOfWeek.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập ngày trong tuần!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etDayStart.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập thời gian bắt đầu!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etDayEnd.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập thời gian kết thúc!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etTime.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập thời lượng!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etRoom.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập phòng học!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (classTeacher == null) {
                Toast.makeText(this, "Vui lòng chọn giảng viên!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Handle add button click event
            Map<String, String> classInfo = new HashMap<>();
            classInfo.put("id", etId.getText().toString());

            assert classId != null;
            assert subjectId != null;

            db.collection("courses").document(subjectId).collection("class_list").document(etId.getText().toString()).set(classInfo);
            classInfo.clear();

            db.collection("teachers").whereEqualTo("name", classTeacher).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        classInfo.put("classId", etId.getText().toString());
                        classInfo.put("classTeacher", classTeacher);
                        classInfo.put("classSubject", classSubject);
                        classInfo.put("dayOfWeek", etDayOfWeek.getText().toString());
                        classInfo.put("timeStart", etDayStart.getText().toString());
                        classInfo.put("timeEnd", etDayEnd.getText().toString());
                        classInfo.put("studentNum", etSize.getText().toString());
                        classInfo.put("time", etTime.getText().toString());
                        classInfo.put("room", etRoom.getText().toString());
                        db.collection("courses_detail").document(etId.getText().toString()).set(classInfo);
                        Toast.makeText(this, "Thêm lớp thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        Button classBtnAddTeacher = findViewById(R.id.btn_add_teacher);
        classBtnAddTeacher.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(this, AdminAddTeacherIntoClass.class);
            intent.putExtra("classId", etId.getText().toString());
            intent.putExtra("subjectId", subjectId);
            intent.putExtra("classTeacher", classTeacher);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("dayOfWeek", etDayOfWeek.getText().toString());
            intent.putExtra("timeStart", etDayStart.getText().toString());
            intent.putExtra("timeEnd", etDayEnd.getText().toString());
            intent.putExtra("studentNum", etSize.getText().toString());
            intent.putExtra("time", etTime.getText().toString());
            intent.putExtra("room", etRoom.getText().toString());
            startActivity(intent);
        });
    }
}