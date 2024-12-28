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
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminTeacherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_teacher, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<TeacherProfile> students = new ArrayList<>();
        AdminTeacherAdapter adapter = new AdminTeacherAdapter(getContext(), students);
        recyclerView.setAdapter(adapter);

        String dateTimeString = "2024-11-16 15:30:00";
        Timestamp timestamp = Timestamp.valueOf(dateTimeString);
        students.add(new TeacherProfile("Pham Minh E", "0868480060", "quanpham0405@gmail.com", timestamp));


        adapter.notifyDataSetChanged();
        return view;
    }
}