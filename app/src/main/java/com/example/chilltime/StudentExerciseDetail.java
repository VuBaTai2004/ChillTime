package com.example.chilltime;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StudentExerciseDetail extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student_exercise_details);

        // Lấy dữ liệu từ Intent
        String exerciseTitle = getIntent().getStringExtra("exerciseTitle");
        String exerciseTime = getIntent().getStringExtra("exerciseTime");
        String exerciseContent = getIntent().getStringExtra("exerciseContent");
        String classId = getIntent().getStringExtra("classId");
        String username = getIntent().getStringExtra("username");

        // Gán dữ liệu vào TextView
        TextView title = findViewById(R.id.text_title);
        TextView time = findViewById(R.id.text_time);
        TextView content = findViewById(R.id.text_content);
        TextView linkTextView = findViewById(R.id.text_link);
        EditText etSubmit = findViewById(R.id.et_submit);
        Button btnSubmit = findViewById(R.id.teacher_btn_submit); // Nút "Nộp bài"

        title.setText("Tiêu đề: " + exerciseTitle);
        time.setText("Thời gian: " + exerciseTime);
        content.setText("Nội dung: " + exerciseContent);

        // Định dạng thời gian và kiểm tra hạn nộp bài
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date exerciseDeadline = dateFormat.parse(exerciseTime);
            Date currentDate = new Date(); // Lấy thời gian hiện tại

            if (exerciseDeadline != null && exerciseDeadline.before(currentDate)) {
                // Nếu đã quá hạn nộp bài
                btnSubmit.setEnabled(false); // Vô hiệu hóa nút
                btnSubmit.setText("Quá hạn"); // Đổi tên nút
                Toast.makeText(this, "Bài tập đã quá hạn, không thể nộp bài.", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi phân tích hạn nộp bài.", Toast.LENGTH_SHORT).show();
        }

        db.collection("exercises")
                .whereEqualTo("classId", classId)
                .whereEqualTo("title", exerciseTitle)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Lấy link tài liệu chính
                        String link = queryDocumentSnapshots.getDocuments().get(0).getString("document");
                        linkTextView.setText(link);

                        // Lấy ID của document bài tập
                        String exerciseDocId = queryDocumentSnapshots.getDocuments().get(0).getId();

                        // Truy vấn link đã nộp bài của username trong submit_homework
                        db.collection("exercises")
                                .document(exerciseDocId)
                                .collection("submit_homework")
                                .whereEqualTo("username", username)
                                .get()
                                .addOnSuccessListener(submitSnapshots -> {
                                    if (!submitSnapshots.isEmpty()) {
                                        // Lấy link đã nộp bài
                                        String submittedLink = submitSnapshots.getDocuments().get(0).getString("link");
                                        etSubmit.setText(submittedLink); // Hiển thị vào EditText
                                    } else {
                                        etSubmit.setText(""); // Không có link, để trống
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Lỗi khi tải link đã nộp bài: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        linkTextView.setText("Không tìm thấy dữ liệu.");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // Xử lý sự kiện khi người dùng nhấn nút "Nộp bài"
        btnSubmit.setOnClickListener(v -> {
            String submittedLink = etSubmit.getText().toString().trim();

            if (submittedLink.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập link.", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("exercises")
                    .whereEqualTo("classId", classId)
                    .whereEqualTo("title", exerciseTitle)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            Map<String, String> submissionInfo = new HashMap<>();
                            submissionInfo.put("username", username);
                            submissionInfo.put("link", submittedLink);

                            db.collection("exercises")
                                    .document(task.getResult().getDocuments().get(0).getId())
                                    .collection("submit_homework")
                                    .add(submissionInfo)
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(this, "Nộp bài thành công.", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Lỗi khi nộp bài: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(this, "Không tìm thấy bài tập tương ứng.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi kiểm tra bài tập: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("exerciseTitle", exerciseTitle); // Truyền tiêu đề bài tập
            setResult(RESULT_OK, resultIntent); // Báo hiệu thành công
            finish();
        });
    }

}
