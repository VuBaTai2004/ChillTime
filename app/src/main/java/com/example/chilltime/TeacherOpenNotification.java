package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TeacherOpenNotification extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.teacher_open_notification);

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

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Notification> notifications = new ArrayList<>();
        TeacherNotificationAdapter adapter = new TeacherNotificationAdapter(notifications, this);
        recyclerView.setAdapter(adapter);

        notifications.add(new Notification("Thông báo 1", "Nội dung thông báo 1"));
        notifications.add(new Notification("Thông báo 2", "Nội dung thông báo 2"));
        notifications.add(new Notification("Thông báo 3", "Nội dung thông báo 3"));

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherOpenNotification.this, TeacherAddNotification.class);
            intent.putExtra("classId", classId);
            intent.putExtra("classSubject", classSubject);
            intent.putExtra("numStu", numStu);
            startActivity(intent);
        });

        db.collection("notifications").whereEqualTo("classId", classId).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        notifications.clear();
                        for(QueryDocumentSnapshot document : task.getResult()){
                            String title = document.getString("title");
                            String message = document.getString("message");
                            notifications.add(new Notification(title, message));
                            Log.d("FirestoreDebug", "Number of documents: " + task.getResult().size());

                        };
                        adapter.notifyDataSetChanged();
                    }
                });

        adapter.notifyDataSetChanged();
    }
}
