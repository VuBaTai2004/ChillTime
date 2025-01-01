package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentTranscriptFragment extends Fragment {
    private static final String TAG = "StudentTranscriptFragment";

    private String username;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String studentId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_transcript, container, false);

        username = getArguments() != null ? getArguments().getString("username") : null;

        RecyclerView recyclerView = view.findViewById(R.id.rv_student_transcript);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (username != null) {
            fetchStudentIdAndTranscripts(username, recyclerView);
        } else {
            Log.e(TAG, "Username is null!");
        }

        return view;
    }

    private void fetchStudentIdAndTranscripts(String username, RecyclerView recyclerView) {
        db.collection("students")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        studentId = task.getResult().getDocuments().get(0).getString("id");
                        fetchTranscripts(studentId, recyclerView);
                    } else {
                        Log.e(TAG, "Student not found");
                    }
                });
    }

    private void fetchTranscripts(String studentId, RecyclerView recyclerView) {
        db.collection("points")
                .whereEqualTo("studentId", studentId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<StudentSemesterTranscript> semesters = new ArrayList<>();

                        task.getResult().forEach(document -> {
                            String classId = document.getString("classId");
                            float progressGrade = Float.parseFloat(document.getString("qt"));
                            float practiceGrade = Float.parseFloat(document.getString("th"));
                            float midtermGrade = Float.parseFloat(document.getString("gk"));
                            float termGrade = Float.parseFloat(document.getString("ck"));

                            float finalGrade = (progressGrade * 0.15f) +
                                    (practiceGrade * 0.15f) +
                                    (midtermGrade * 0.30f) +
                                    (termGrade * 0.40f);

                            fetchCourseDetailsAndUpdateRecyclerView(classId, semesters, studentId, progressGrade, practiceGrade, midtermGrade, termGrade, finalGrade, recyclerView);
                        });
                    } else {
                        Log.e(TAG, "Error fetching points", task.getException());
                    }
                });
    }

    private void fetchCourseDetailsAndUpdateRecyclerView(String classId,
                                                         List<StudentSemesterTranscript> semesters,
                                                         String studentId,
                                                         float progressGrade,
                                                         float practiceGrade,
                                                         float midtermGrade,
                                                         float termGrade,
                                                         float finalGrade,
                                                         RecyclerView recyclerView) {
        db.collection("courses_detail")
                .whereEqualTo("classId", classId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    querySnapshot.forEach(courseDoc -> {
                        String startTime = courseDoc.getString("timeStart"); // Format YYYY-MM
                        String semesterName = calculateSemesterName(startTime);

                        // Thêm môn học vào học kỳ
                        addCourseToSemester(semesters, semesterName, new StudentTranscript(
                                studentId, classId, progressGrade, practiceGrade, midtermGrade, termGrade, finalGrade
                        ));

                        // Cập nhật RecyclerView sau mỗi lần thêm
                        SemesterTranscriptAdapter adapter = new SemesterTranscriptAdapter(semesters);
                        recyclerView.setAdapter(adapter);
                    });
                });
    }

    private String calculateSemesterName(String timeStart) {
        if (timeStart == null) return "Unknown Semester";

        try {
            // Định dạng ngày tháng theo "dd/MM/yyyy"
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = dateFormat.parse(timeStart); // Phân tích chuỗi thành đối tượng Date

            // Lấy tháng và năm từ đối tượng Date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Tháng trong Calendar bắt đầu từ 0

            // Tính học kỳ dựa trên tháng
            return (month >= 8) ? "HK1 " + year : "HK2 " + (year - 1);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing timeStart", e);
            return "Unknown Semester";
        }
    }

    private void addCourseToSemester(List<StudentSemesterTranscript> semesters, String semesterName, StudentTranscript course) {
        for (StudentSemesterTranscript semester : semesters) {
            if (semester.getSemesterName().equals(semesterName)) {
                semester.getCourses().add(course);
                return;
            }
        }

        List<StudentTranscript> newCourses = new ArrayList<>();
        newCourses.add(course);
        semesters.add(new StudentSemesterTranscript(semesterName, newCourses));
    }
}
