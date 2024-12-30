package com.example.chilltime;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class TeacherOpenBoard extends AppCompatActivity {
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
        List<List<String>> data = Arrays.asList(
                Arrays.asList("STT", "Họ và tên", "QT", "TH", "GK", "CK", "TB"),
                Arrays.asList("1", "Phạm Minh Quân", "5", "5", "5", "5", "5"),
                Arrays.asList("2", "Nguyễn Văn A", "7", "6", "8", "7", "7"),
                Arrays.asList("3", "Trần Thị B", "9", "8", "10", "9", "9"),
                Arrays.asList("1", "Phạm Minh Quân", "5", "5", "5", "5", "5"),
                Arrays.asList("2", "Nguyễn Văn A", "7", "6", "8", "7", "7"),
                Arrays.asList("3", "Trần Thị B", "9", "8", "10", "9", "9"),
                Arrays.asList("1", "Phạm Minh Quân", "5", "5", "5", "5", "5"),
                Arrays.asList("2", "Nguyễn Văn A", "7", "6", "8", "7", "7"),
                Arrays.asList("3", "Trần Thị B", "9", "8", "10", "9", "9"),
                Arrays.asList("1", "Phạm Minh Quân", "5", "5", "5", "5", "5"),
                Arrays.asList("2", "Nguyễn Văn A", "7", "6", "8", "7", "7"),
                Arrays.asList("3", "Trần Thị B", "9", "8", "10", "9", "9"),
                Arrays.asList("1", "Phạm Minh Quân", "5", "5", "5", "5", "5"),
                Arrays.asList("2", "Nguyễn Văn A", "7", "6", "8", "7", "7"),
                Arrays.asList("3", "Trần Thị B", "9", "8", "10", "9", "9"),
                Arrays.asList("1", "Phạm Minh Quân", "5", "5", "5", "5", "5"),
                Arrays.asList("2", "Nguyễn Văn A", "7", "6", "8", "7", "7"),
                Arrays.asList("3", "Trần Thị B", "9", "8", "10", "9", "9"),
                Arrays.asList("1", "Phạm Minh Quân", "5", "5", "5", "5", "5"),
                Arrays.asList("2", "Nguyễn Văn A", "7", "6", "8", "7", "7"),
                Arrays.asList("3", "Trần Thị B", "9", "8", "10", "9", "9"),
                Arrays.asList("1", "Phạm Minh Quân", "5", "5", "5", "5", "5"),
                Arrays.asList("2", "Nguyễn Văn A", "7", "6", "8", "7", "7"),
                Arrays.asList("3", "Trần Thị B", "9", "8", "10", "9", "9")

        );

        TeachBoardAdapter adapter = new TeachBoardAdapter(this,data);
        recyclerView.setAdapter(adapter);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            onBackPressed();
        });


        adapter.notifyDataSetChanged();


    }
}
