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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminSubjectFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_subject, container,false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<StudentClass> subjects = new ArrayList<>();
        AdminSubjectAdapter adapter = new AdminSubjectAdapter(getContext(), subjects);
        recyclerView.setAdapter(adapter);


        subjects.add(new StudentClass("NT131.P13", "Hệ thống Nhúng mạng không dây", "10", "Nguyễn Văn A"));
        subjects.add(new StudentClass("NT532.P11", "Công nghệ Internet of things hiện đại",  "10", "Nguyễn Văn B"));


        EditText et = view.findViewById(R.id.et_class_search);
        adapter.notifyDataSetChanged();
        return view;
    }
}