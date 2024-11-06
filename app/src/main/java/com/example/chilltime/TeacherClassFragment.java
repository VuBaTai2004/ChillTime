package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class TeacherClassFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_teacher_class, container, false);
        ArrayList<AdminClass> adminClasses = new ArrayList<>();
        adminClasses.add(new AdminClass("NT131.P13", "Hệ thống Nhúng mạng không dây"));
        adminClasses.add(new AdminClass("NT532.P11","Công nghệ Internet of things hiện đại"));
        adminClasses.add(new AdminClass("NT118.P13", "Phát triển ứng dụng trên thiết bị di động"));


        RecyclerView recyclerView = view.findViewById(R.id.rv_student_class);
        AdminClassAdapter adapter = new AdminClassAdapter(view.getContext(), adminClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        Spinner spn = view.findViewById(R.id.spn_class_search);
        String[] spnStr = {"Tìm theo mã số", "Tìm theo tên"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, spnStr);
        spn.setAdapter(stringArrayAdapter);

        Button btn = view.findViewById(R.id.btn_class_search);
        EditText et = view.findViewById(R.id.et_class_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = et.getText().toString().trim();
                String filterType = spn.getSelectedItem().toString();
                adapter.filter(query, filterType);
            }
        });
        return view;
    }
}