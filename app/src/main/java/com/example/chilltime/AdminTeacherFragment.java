package com.example.chilltime;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class AdminTeacherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_teacher, container, false);
        ArrayList<AdminTeacher> teacherArrayList = new ArrayList<>();
        String[] spnStr = {"Tìm theo mã số", "Tìm theo tên"};
        RecyclerView rv = view.findViewById(R.id.rv_teacher_list);
        Spinner spn = view.findViewById(R.id.spn_teacher_search);
        Button btn = view.findViewById(R.id.btn_teacher_add);

        teacherArrayList.add(new AdminTeacher("1", "Nguyễn Văn A", "anv1@gmail.com", "0123456787"));
        teacherArrayList.add(new AdminTeacher("2", "Nguyễn Văn B", "anv2@gmail.com", "0123456788"));
        teacherArrayList.add(new AdminTeacher("3", "Nguyễn Văn C", "anv3@gmail.com", "0123456789"));

        AdminTeacherAdapter adapter = new AdminTeacherAdapter(view.getContext(), teacherArrayList);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setAdapter(adapter);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, spnStr);
        spn.setAdapter(stringArrayAdapter);

        btn.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminTeacherAddFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        return view;
    }
}