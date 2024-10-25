package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        RecyclerView recyclerView = view.findViewById(R.id.rv_class_list);
        AdminClassAdapter adapter = new AdminClassAdapter(view.getContext(), adminClasses);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}