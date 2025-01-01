package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentTranscriptFragment extends Fragment {
    String username;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_transcript, container, false);

        username = getArguments() != null ? getArguments().getString("username") : null;

        if (username != null) {
            db.collection("students")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                id = document.getString("id");
                            }
                        } else {
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                        }
                    });
        }

        RecyclerView recyclerView = view.findViewById(R.id.rv_student_transcript);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<StudentSemesterTranscript> allSemesters = getAllSemestersTranscript();
        SemesterTranscriptAdapter adapter = new SemesterTranscriptAdapter(allSemesters);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<StudentSemesterTranscript> getAllSemestersTranscript() {
        List<StudentSemesterTranscript> semesters = new ArrayList<>();

        // Semester 1 example
        List<StudentTranscript> semester1Courses = new ArrayList<>();
        semester1Courses.add(new StudentTranscript("IDSV1","IT001.P11", 8.0f, 7.5f, 7.0f, 7.5f));
        semester1Courses.add(new StudentTranscript("IDSV1","IT002.P12", 8.0f, 7.5f, 7.0f, 7.5f));
        semesters.add(new StudentSemesterTranscript("HK1 2023-2024", semester1Courses));

        // Semester 2 example
        List<StudentTranscript> semester2Courses = new ArrayList<>();
        semester2Courses.add(new StudentTranscript("IDSV1","IT003.P13", 3, 7.5f, 8.0f, 7.8f, 8.2f));
        semesters.add(new StudentSemesterTranscript("HK2 2023-2024", semester2Courses));

        return semesters;
    }

    private StudentTranscript studentTranscript(){

        db.collection("points")
                .whereEqualTo("studentId", id)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        try {
                            // Lấy các giá trị từ document và chuyển sang số
                            String classId = document.getString("classId");
                            float progressGrade = Float.parseFloat(document.getString("qt"));
                            float practiceGrade = Float.parseFloat(document.getString("th"));
                            float midtermGrade = Float.parseFloat(document.getString("gk"));
                            float termGrade = Float.parseFloat(document.getString("ck"));

                            // Tính điểm cuối cùng
                            float finalGrade = (float) ((progressGrade * 0.15) + (practiceGrade * 0.15) + (midtermGrade * 0.30) + (termGrade * 0.40));
                            StudentTranscript transcript = new StudentTranscript(id, classId, progressGrade, practiceGrade, midtermGrade, termGrade, finalGrade);

                        } catch (NumberFormatException e) {
                        }
                    }
                })
                .addOnFailureListener(e -> {
                });
    }
}