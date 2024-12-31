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

//        db.collection("points").whereEqualTo("classId", classId).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            String id = document.getString("id");
//                            String qt = document.getString("qt");
//                            String th = document.getString("th");
//                            String gk = document.getString("gk");
//                            String ck = document.getString("ck");
//                            db.collection("students").whereEqualTo("id", document.getString("id")).get()
//                                    .addOnCompleteListener(task1 -> {
//                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
//                                            String name = document1.getString("name");
//                                            int stt = data.size();
//                                            data.add(Arrays.asList(String.valueOf(stt), name, qt, th, gk, ck, "TB"));
//
//                                            Log.d("students_points", "data: " + name);
//                                        }
//                                        adapter.notifyDataSetChanged();
//                                    });
//                            Log.d("points", "data: " + document.getData());
//                        }
//                    }
//                });
        db.collection("points").whereEqualTo("classId", classId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        data.clear();
                        data.add(Arrays.asList("STT", "Họ và tên", "QT", "TH", "GK", "CK", "TB"));

                        // Tạo một Map để lưu thông tin sinh viên
                        Map<String, String> studentMap = new HashMap<>();
                        db.collection("students").get()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot studentDoc : task1.getResult()) {
                                            studentMap.put(studentDoc.getString("id"), studentDoc.getString("name"));
                                        }

                                        // Sau khi có dữ liệu sinh viên, xử lý điểm
                                        int stt = 1;
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String studentId = document.getString("studentId");
                                            String name = studentMap.getOrDefault(studentId, "Unknown");
                                            String qt = document.getString("qt");
                                            String th = document.getString("th");
                                            String gk = document.getString("gk");
                                            String ck = document.getString("ck");

                                            data.add(Arrays.asList(String.valueOf(stt), name, qt, th, gk, ck, "TB"));
                                            stt++;
                                        }

                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Log.w("err", "Error getting student documents.", task1.getException());
                                    }
                                });
                    } else {
                        Log.w("err", "Error getting points documents.", task.getException());
                    }
                });



        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });


        adapter.notifyDataSetChanged();


    }
}
