package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TeacherClassFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_class, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_class_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ArrayList<TeacherClass> classes = new ArrayList<>();
        TeacherClassAdapter adapter = new TeacherClassAdapter(view.getContext(), classes);
        recyclerView.setAdapter(adapter);

        classes.add(new TeacherClass("NT131.P13", "Hệ thống Nhúng mạng không dây", "10", "Nguyễn Văn A"));
        classes.add(new TeacherClass("NT532.P11", "Công nghệ Internet of things hiện đại",  "10", "Nguyễn Văn B"));

        String username = getArguments() != null ? getArguments().getString("username") : null;

        db.collection("teachers").whereEqualTo("username", username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        classes.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String documentId = document.getId();

                            db.collection("teachers").document(documentId).collection("class_list").get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                TeacherClass teacherClass = new TeacherClass(
                                                        document1.getString("classId"),
                                                        document1.getString("classSubject"),
                                                        document1.getString("studentNum"),
                                                        document1.getString("classTeacher")
                                                );
                                                classes.add(teacherClass);
                                            }
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            Log.w("Firestore", "Error getting class list: ", task1.getException());
                                        }
                                    });
                        }
                    } else {
                        Log.w("Firestore", "No teacher found or error: ", task.getException());
                    }
                });


        EditText etSearch = view.findViewById(R.id.et_class_search);
        etSearch.setOnClickListener(v -> {

        });

        return view;
    }
}