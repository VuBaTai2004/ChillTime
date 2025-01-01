package com.example.chilltime;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentNotificationFragment extends Fragment {
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private List<Subject> subjects;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_notification, container, false);

        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerViewSubjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        subjects = new ArrayList<>();
        adapter = new SubjectAdapter(subjects);
        recyclerView.setAdapter(adapter);

        // Lấy `username` từ arguments
        String username = getArguments() != null ? getArguments().getString("username") : null;

        if (username != null) {
            fetchStudentIdByUsername(username);
        } else {
            Log.e("StudentNotification", "No username provided");
        }

        return view;
    }

    private void fetchStudentIdByUsername(String username) {
        db.collection("students")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        String studentId = task.getResult().getDocuments().get(0).getId();
                        Log.d("StudentNotification", "Found studentId: " + studentId);
                        fetchStudentClasses(studentId);
                    } else {
                        Log.e("Firestore", "No student found with username: " + username);
                    }
                });
    }

    private void fetchStudentClasses(String studentId) {
        db.collection("students")
                .document(studentId)
                .collection("class_list")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> classIds = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String classId = document.getString("classId");
                            if (classId != null) {
                                classIds.add(classId);
                            }
                        }
                        fetchClassDetails(classIds);
                    } else {
                        Log.e("Firestore", "Error fetching class list", task.getException());
                    }
                });
    }

    private void fetchClassDetails(List<String> classIds) {
        for (String classId : classIds) {
            db.collection("courses_detail")
                    .document(classId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("classSubject");
                            List<Notification> notifications = new ArrayList<>();
                            List<Exercise> exercises = new ArrayList<>();

                            fetchNotifications(classId, notifications, () -> {
                                fetchExercises(classId, exercises, () -> {
                                    Subject subject = new Subject(name, notifications, exercises);
                                    subjects.add(subject);

                                    // Cập nhật adapter trên luồng chính
                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                                    }
                                });
                            });
                        }
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error fetching class details", e));
        }
    }

    private void fetchNotifications(String classId, List<Notification> notifications, Runnable onComplete) {
        db.collection("notifications")
                .whereEqualTo("classId", classId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("title");
                            String content = document.getString("content");
                            notifications.add(new Notification(title, content));
                        }
                    } else {
                        Log.e("Firestore", "Error fetching notifications", task.getException());
                    }
                    onComplete.run();
                });
    }

    private void fetchExercises(String classId, List<Exercise> exercises, Runnable onComplete) {
        db.collection("exercises")
                .whereEqualTo("classId", classId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("title");
                            String time = document.getString("time");
                            String content = document.getString("content");
                            exercises.add(new Exercise(title, time, content));
                        }
                    } else {
                        Log.e("Firestore", "Error fetching exercises", task.getException());
                    }
                    onComplete.run();
                });
    }
}
