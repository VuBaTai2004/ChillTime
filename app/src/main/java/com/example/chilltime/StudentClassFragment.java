package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class StudentClassFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_class, container, false);
        // Class list
        ArrayList<AdminClass> StudentClasses = new ArrayList<>();
        StudentClasses.add(new AdminClass("NT131.P13", "Hệ thống Nhúng mạng không dây"));
        StudentClasses.add(new AdminClass("NT532.P11","Công nghệ Internet of things hiện đại"));
        StudentClasses.add(new AdminClass("NT118.P13", "Phát triển ứng dụng trên thiết bị di động"));



        Spinner spinnerYear = view.findViewById(R.id.spn_year);
        ArrayList<String> arrayYear = new ArrayList<String>();
        arrayYear.add("2023");
        arrayYear.add("2024");
        arrayYear.add("2025");
        ArrayAdapter<String>  stringArrayAdapterYear = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayYear);
        spinnerYear.setAdapter(stringArrayAdapterYear);
        Spinner spinnerSemester = view.findViewById(R.id.spn_semester);
        ArrayList<String> arraySemester = new ArrayList<String>();
        arraySemester.add("Học kỳ 1");
        arraySemester.add("Học kỳ 2");

        ArrayAdapter<String>  stringArrayAdapterSemester = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, arraySemester);
        spinnerSemester.setAdapter(stringArrayAdapterSemester);
        return view;

    }
}