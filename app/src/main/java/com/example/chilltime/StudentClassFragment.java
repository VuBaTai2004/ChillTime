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
import java.util.List;

public class StudentClassFragment extends Fragment {
    private RecyclerView recyclerView;
    private StudentClassAdapter adapter;
    private List<StudentClass> studentClassList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_class, container, false);
        // Class list

        recyclerView = view.findViewById(R.id.rv_student_class);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // Initialize data
        studentClassList = new ArrayList<>();
        studentClassList.add(new StudentClass("001", "Mạng xã hội", "15/3/2024", "20/9/2024"));
        studentClassList.add(new StudentClass("002", "Toán cao cấp", "15/3/2024", "15/9/2024"));
        studentClassList.add(new StudentClass("002", "Kinh tế", "15/3/2024", "15/9/2024"));
        // Thêm dữ liệu mẫu khác vào danh sách studentClassList

        adapter = new StudentClassAdapter(view.getContext(), studentClassList);
        recyclerView.setAdapter(adapter);

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