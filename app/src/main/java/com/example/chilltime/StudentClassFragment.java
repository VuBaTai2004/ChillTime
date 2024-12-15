package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class StudentClassFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_class, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_class_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ArrayList<TeacherClass> classes = new ArrayList<>();
        StudentClassAdapter adapter = new StudentClassAdapter(view.getContext(), classes);
        recyclerView.setAdapter(adapter);

        classes.add(new TeacherClass("NT131.P13", "Hệ thống Nhúng mạng không dây", "10", "Nguyễn Văn A"));
        classes.add(new TeacherClass("NT532.P11", "Công nghệ Internet of things hiện đại",  "10", "Nguyễn Văn B"));


        EditText et = view.findViewById(R.id.et_class_search);


        adapter.notifyDataSetChanged();
        return view;

    }
}