package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminStudentFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_student, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<StudentProfile> students = new ArrayList<>();
        AdminStudentAdapter adapter = new AdminStudentAdapter(getContext(), students);
        recyclerView.setAdapter(adapter);

        students.add(new StudentProfile("Pham Minh E","120", "0868480060", "quanpham0405@gmail.com"));

        FloatingActionButton add = view.findViewById(R.id.add);
        add.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(getContext(), AdminAddStudent.class);
            startActivity(intent);
        });

        db.collection("students").orderBy("id")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        students.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            StudentProfile student = new StudentProfile(
                                    document.getString("name"),
                                    document.getString("id"),  // Thêm trường id
                                    "0000000000",
                                    document.getString("email")
                            );
                            students.add(student);
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