package com.example.chilltime;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherOpenBoard extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.teacher_open_board);

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

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHeader);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dữ liệu mẫu
        List<List<String>> data = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("STT", "Họ và tên", "QT", "TH", "GK", "CK", "TB"))
        ));

        TeachBoardAdapter adapter = new TeachBoardAdapter(this,data);
        recyclerView.setAdapter(adapter);

        db.collection("points").whereEqualTo("classId", classId).get()
                .addOnCompleteListener(pointsTask -> {
                    if (pointsTask.isSuccessful()) {
                        List<Map<String, Object>> pointsList = new ArrayList<>();
                        for (QueryDocumentSnapshot pointDoc : pointsTask.getResult()) {
                            pointsList.add(pointDoc.getData());
                        }

                        // Lặp qua từng studentId trong danh sách points
                        for (Map<String, Object> point : pointsList) {
                            String studentId = (String) point.get("studentId");
                            float progressGrade = Float.parseFloat((String) point.get("qt"));
                            float practiceGrade = Float.parseFloat((String) point.get("th"));
                            float midtermGrade = Float.parseFloat((String) point.get("gk"));
                            float termGrade = Float.parseFloat((String) point.get("ck"));
                            float finalGrade = (float) ((progressGrade * 0.15) + (practiceGrade * 0.15) + (midtermGrade * 0.30) + (termGrade * 0.40));

                            // Truy vấn vào collection "students" để lấy tên
                            db.collection("students").whereEqualTo("id", studentId).get()
                                    .addOnCompleteListener(studentTask -> {
                                        if (studentTask.isSuccessful() && !studentTask.getResult().isEmpty()) {
                                            for (QueryDocumentSnapshot studentDoc : studentTask.getResult()) {
                                                String studentName = (String) studentDoc.get("name");

                                                // Thêm dữ liệu vào danh sách hiển thị
                                                data.add(new ArrayList<>(Arrays.asList(
                                                        studentId,  // Sử dụng studentId thay vì STT
                                                        studentName,
                                                        String.valueOf(progressGrade),
                                                        String.valueOf(practiceGrade),
                                                        String.valueOf(midtermGrade),
                                                        String.valueOf(termGrade),
                                                        String.format("%.2f", finalGrade)
                                                )));
                                                adapter.notifyDataSetChanged();
                                            }
                                        } else {
                                            Log.w("Firestore", "Không tìm thấy thông tin student với ID: " + studentId);
                                        }
                                    });
                        }
                    } else {
                        Log.w("Firestore", "Lỗi khi lấy danh sách điểm: ", pointsTask.getException());
                    }
                });



        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });


        adapter.notifyDataSetChanged();


    }
}