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

        List<List<StudentTranscript>> allSemesters = getAllSemestersTranscript();
        SemesterTranscriptAdapter adapter = new SemesterTranscriptAdapter(allSemesters);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<List<StudentTranscript>> getAllSemestersTranscript(){
        List<List<StudentTranscript>> semesters = new ArrayList<>();

        // Ví dụ: Học kỳ 1 năm học 2023-2024
        List<StudentTranscript> semester1 = new ArrayList<>();
        semester1.add(new StudentTranscript("IDSV1", "HK1 2023-2024", "IT001.P11", 4, 8.0f, 7.5f, 7.0f, 7.5f));
        semester1.add(new StudentTranscript("IDSV1", "HK1 2023-2024", "IT002.P12", 4, 8.0f, 7.5f, 7.0f, 7.5f));

        // Học kỳ 2 năm học 2023-2024
        List<StudentTranscript> semester2 = new ArrayList<>();
        semester2.add(new StudentTranscript("IDSV1", "HK2 2023-2024", "IT003.P13", 3, 7.5f, 8.0f, 7.8f, 8.2f));

        semesters.add(semester1);
        semesters.add(semester2);

        return semesters;
    }
}