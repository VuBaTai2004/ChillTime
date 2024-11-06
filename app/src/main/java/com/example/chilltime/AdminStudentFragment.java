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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class AdminStudentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_student, container, false);
        ArrayList<AdminStudent> studentArrayList = new ArrayList<>();
        String[] spnStr = {"Tìm theo mã số", "Tìm theo tên"};
        RecyclerView rv = view.findViewById(R.id.rv_student_list);
        Spinner spn = view.findViewById(R.id.spn_student_search);
        Button btn = view.findViewById(R.id.btn_student_add);

        studentArrayList.add(new AdminStudent("1", "Nguyễn Văn A", "anv1@gmail.com", "0123456787"));
        studentArrayList.add(new AdminStudent("2", "Nguyễn Văn B", "anv2@gmail.com", "0123456788"));
        studentArrayList.add(new AdminStudent("3", "Nguyễn Văn C", "anv3@gmail.com", "0123456789"));

        AdminStudentAdapter adapter = new AdminStudentAdapter(view.getContext(), studentArrayList);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setAdapter(adapter);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, spnStr);
        spn.setAdapter(stringArrayAdapter);

        btn.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminStudentAddFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        return view;
    }
}