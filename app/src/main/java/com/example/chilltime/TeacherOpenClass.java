package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherOpenClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_open_class);
        EdgeToEdge.enable(this);


        // Lấy dữ liệu từ Intent
        String classId = getIntent().getStringExtra("classId");
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

        ImageView arrowIcon1 = findViewById(R.id.arrow_icon1);
        ImageView arrowIcon2 = findViewById(R.id.arrow_icon2);
        ImageView arrowIcon3 = findViewById(R.id.arrow_icon3);
        ImageView arrowIcon4 = findViewById(R.id.arrow_icon4);

        arrowIcon1.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon1 được nhấn
            Intent intent = new Intent(TeacherOpenClass.this, TeacherOpenList.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

        arrowIcon2.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon2 được nhấn
        });

        arrowIcon3.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon3 được nhấn
        });

        arrowIcon4.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon4 được nhấn
        });


    }

}
