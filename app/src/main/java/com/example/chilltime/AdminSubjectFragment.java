package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class AdminSubjectFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdminSubjectAdapter adapter;
    private ArrayList<AdminSubject> subjectList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_subject, container,false);

        recyclerView=rootView.findViewById(R.id.rv_admin_subject_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        subjectList=new ArrayList<>();
        subjectList.add(new AdminSubject("Nhập môn lập trình  ", "IT001.P01"));
        subjectList.add(new AdminSubject("Phát triển ứng dụng trên thiết bị di động", "NT118.P21"));
        subjectList.add(new AdminSubject("Công nghệ Internet Of Things hiện đại", "NT532.P13"));
        subjectList.add(new AdminSubject("Lịch sử Đảng Cộng sản Việt Nam", "SS010.P30"));
        subjectList.add(new AdminSubject("Khóa luận tốt nghiệp", "NT505.P51"));

        adapter=new AdminSubjectAdapter(getContext(),subjectList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}