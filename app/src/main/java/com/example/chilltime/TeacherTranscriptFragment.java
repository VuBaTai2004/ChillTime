package com.example.chilltime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;

public class TeacherTranscriptFragment extends Fragment {
    private Spinner yearSpinner;
    private Spinner classSpinner;
    private RecyclerView recyclerView;
    private TeacherTransciptAdapter adapter;
    private ArrayList<TeacherTranscipt> transcriptList = new ArrayList<>();
    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> classes = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_transcript, container, false);

        yearSpinner = view.findViewById(R.id.spn_year_search);
        classSpinner = view.findViewById(R.id.spn_class_search);
        recyclerView = view.findViewById(R.id.rv_class_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TeacherTransciptAdapter(getContext(), transcriptList);
        recyclerView.setAdapter(adapter);

        addSampleData();

        extractUniqueYearsAndClasses();

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(yearAdapter);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, classes);
        classSpinner.setAdapter(classAdapter);

        // Cài đặt sự kiện cho Spinner
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterTranscriptList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterTranscriptList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return view;
    }

    private void addSampleData() {
        transcriptList.add(new TeacherTranscipt("22521243", "Nguyễn Văn I", "8", "7", "8", "9","8.2", "2023", "Class A"));
        transcriptList.add(new TeacherTranscipt("22521324", "Phạm Minh Z", "9", "6", "7", "8","7.4", "2023", "Class B"));
        transcriptList.add(new TeacherTranscipt("22524123", "Vũ Bá W", "7", "8", "9", "10", "9","2024", "Class A"));

    }

    private void extractUniqueYearsAndClasses() {
        HashSet<String> yearSet = new HashSet<>();
        HashSet<String> classSet = new HashSet<>();

        for (TeacherTranscipt transcript : transcriptList) {
            yearSet.add(transcript.getYear());
            classSet.add(transcript.getStudentClass());
        }

        years.clear();
        years.addAll(yearSet);
        classes.clear();
        classes.addAll(classSet);
    }

    private void filterTranscriptList() {
        String selectedYear = (String) yearSpinner.getSelectedItem();
        String selectedClass = (String) classSpinner.getSelectedItem();

        if (selectedYear != null && selectedClass != null) {
            ArrayList<TeacherTranscipt> filteredList = new ArrayList<>();
            for (TeacherTranscipt transcript : transcriptList) {
                if (transcript.getYear().equals(selectedYear) && transcript.getStudentClass().equals(selectedClass)) {
                    filteredList.add(transcript);
                }
            }
            adapter.setFilteredList(filteredList);
        } else {
            adapter.setFilteredList(new ArrayList<>());
        }
    }
}