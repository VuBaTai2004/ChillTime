package com.example.chilltime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class StudentNotificationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_notification, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewSubjects);

        List<Notification> notifications = Arrays.asList(
                new Notification("Thông báo 1", "Mai vẫn đi học"),
                new Notification("Thông báo 2", "Kiểm tra tuần sau")
        );
        List<Exercise> exercises = Arrays.asList(
                new Exercise("Lab 1", "2h ngày 13/11", "Tài liệu: document.pdf"),
                new Exercise("Lab 2", "2h ngày 20/11", "Tài liệu: slides.pdf")
        );

        List<Subject> subjects = Arrays.asList(
                new Subject("Hệ thống nhúng", notifications, exercises),
                new Subject("Lập trình hệ thống", notifications, exercises)
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SubjectAdapter(subjects));
        return view;
    }
}
