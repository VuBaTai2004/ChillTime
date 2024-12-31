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

import java.util.ArrayList;
import java.util.List;

public class StudentTranscriptFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_transcript, container, false);

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
}