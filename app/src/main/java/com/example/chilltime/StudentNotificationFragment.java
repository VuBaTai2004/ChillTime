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
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentNotificationFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private List<Subject> subjects = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_notification, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSubjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter with empty list
        adapter = new SubjectAdapter(subjects);
        recyclerView.setAdapter(adapter);

        String studentId = getArguments() != null ? getArguments().getString("studentId") : null;

        if (studentId != null) {
            fetchStudentClasses(studentId);
        }

        return view;
    }

    private void fetchStudentClasses(String studentId) {
        db.collection("students")
                .document(studentId)
                .collection("class_list")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> classIds = new ArrayList<>();
                        for (QueryDocumentSnapshot classDoc : task.getResult()) {
                            String classId = classDoc.getString("classId");
                            if (classId != null) {
                                classIds.add(classId);
                            }
                        }
                        if (!classIds.isEmpty()) {
                            fetchClassDetails(classIds);
                        } else {
                            Log.w("Firestore", "No classes found for studentId: " + studentId);
                        }
                    } else {
                        Log.e("Firestore", "Error fetching class list: ", task.getException());
                    }
                });
    }

    private void fetchClassDetails(List<String> classIds) {
        for (String classId : classIds) {
            List<Exercise> classExercises = new ArrayList<>();
            List<Notification> classNotifications = new ArrayList<>();

            fetchExercises(classId, classExercises, () -> {
                fetchNotifications(classId, classNotifications, () -> {
                    Subject subject = new Subject(classId, classNotifications, classExercises);
                    subjects.add(subject);

                    // Update adapter on the main thread
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                    }
                });
            });
        }
    }

    private void fetchExercises(String classId, List<Exercise> classExercises, Runnable onComplete) {
        db.collection("exercises")
                .whereEqualTo("classId", classId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("title");
                            String time = document.getString("time");
                            String content = document.getString("content");
                            classExercises.add(new Exercise(title, time, content));
                        }
                    } else {
                        Log.e("Firestore", "Error fetching exercises for classId: " + classId, task.getException());
                    }
                    onComplete.run();
                });
    }

    private void fetchNotifications(String classId, List<Notification> classNotifications, Runnable onComplete) {
        db.collection("notifications")
                .whereEqualTo("classId", classId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("title");
                            String content = document.getString("content");
                            classNotifications.add(new Notification(title, content));
                        }
                    } else {
                        Log.e("Firestore", "Error fetching notifications for classId: " + classId, task.getException());
                    }
                    onComplete.run();
                });
    }
}
