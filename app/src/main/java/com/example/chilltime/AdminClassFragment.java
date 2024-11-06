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
import android.widget.Spinner;

import java.util.ArrayList;

public class AdminClassFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_class, container, false);
        ArrayList<AdminClass> adminClasses = new ArrayList<>();
        adminClasses.add(new AdminClass("NT131.P13", "Hệ thống Nhúng mạng không dây"));
        adminClasses.add(new AdminClass("NT532.P11","Công nghệ Internet of things hiện đại"));
        adminClasses.add(new AdminClass("NT118.P13", "Phát triển ứng dụng trên thiết bị di động"));

        Button btn = view.findViewById(R.id.btn_class_add);
        btn.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminClassAddFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_student_class);
        AdminClassAdapter adapter = new AdminClassAdapter(view.getContext(), adminClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        Spinner spn = view.findViewById(R.id.spn_class_search);
        String[] spnStr = {"Tìm theo mã số", "Tìm theo tên"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, spnStr);
        spn.setAdapter(stringArrayAdapter);

        return view;
    }
}