package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class TeacherOpenClass extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
            Intent intent = new Intent(TeacherOpenClass.this, TeacherOpenBoard.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

        arrowIcon3.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon3 được nhấn
            Intent intent = new Intent(TeacherOpenClass.this, TeacherOpenExercise.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

        arrowIcon4.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon4 được nhấn
            Intent intent = new Intent(TeacherOpenClass.this, TeacherOpenNotification.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

        ConstraintLayout classListContainer = findViewById(R.id.class_list_container1);
        classListContainer.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherOpenClass.this, TeacherOpenList.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

        ConstraintLayout classListContainer2 = findViewById(R.id.class_list_container2);
        classListContainer2.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherOpenClass.this, TeacherOpenBoard.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

        ConstraintLayout classListContainer3 = findViewById(R.id.class_list_container3);
        classListContainer3.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon3 được nhấn
            Intent intent = new Intent(TeacherOpenClass.this, TeacherOpenExercise.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

        ConstraintLayout classListContainer4 = findViewById(R.id.class_list_container4);
        classListContainer4.setOnClickListener(v -> {
            // Xử lý sự kiện khi arrow_icon4 được nhấn
            Intent intent = new Intent(TeacherOpenClass.this, TeacherOpenNotification.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });


    }

}
