package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminTeacherFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_teacher, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<TeacherProfile> teachers = new ArrayList<>();
        AdminTeacherAdapter adapter = new AdminTeacherAdapter(getContext(), teachers);
        recyclerView.setAdapter(adapter);

        teachers.add(new TeacherProfile("Pham Minh E","120", "0868480060", "quanpham0405@gmail.com"));

        FloatingActionButton add = view.findViewById(R.id.add);
        add.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(getContext(), AdminAddTeacher.class);
            startActivity(intent);
        });

        db.collection("teachers").orderBy("id")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        teachers.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            TeacherProfile student = new TeacherProfile(
                                    document.getString("name"),
                                    document.getString("id"),  // Thêm trường id
                                    "0000000000",
                                    document.getString("email")
                            );
                            teachers.add(student);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("err", "Error getting documents.", task.getException());
                    }
                });

        adapter.notifyDataSetChanged();
        return view;
    }
}