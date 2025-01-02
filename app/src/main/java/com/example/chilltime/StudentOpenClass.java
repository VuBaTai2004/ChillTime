package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class StudentOpenClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_open_class);
        EdgeToEdge.enable(this);

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

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        ImageView arrowIcon3 = findViewById(R.id.arrow_icon3);
        ImageView arrowIcon4 = findViewById(R.id.arrow_icon4);

        ConstraintLayout classListContainer3 = findViewById(R.id.class_list_container3);
        classListContainer3.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon3 được nhấn
            Intent intent = new Intent(StudentOpenClass.this, StudentOpenExercise.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            intent.putExtra("classTeacher", classTeacher);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        ConstraintLayout classListContainer4 = findViewById(R.id.class_list_container4);
        classListContainer4.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon4 được nhấn
            Intent intent = new Intent(StudentOpenClass.this, StudentOpenNotification.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            intent.putExtra("classTeacher", classTeacher);

            startActivity(intent);
        });
    }


}
