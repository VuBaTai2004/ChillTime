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
import java.util.List;

public class TeacherClassFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<TeacherClass> classes = new ArrayList<>();
    private TeacherClassAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_class, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_class_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new TeacherClassAdapter(view.getContext(), classes);
        recyclerView.setAdapter(adapter);

        // Fetch data from Firestore
        String username = getArguments() != null ? getArguments().getString("username") : null;
        if (username != null) {
            fetchTeacherClasses(username);
        } else {
            Log.e("TeacherClassFragment", "Username is null, cannot load classes.");
        }

        EditText etSearch = view.findViewById(R.id.et_class_search);
        etSearch.setText("");
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call filter function on adapter
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        return view;
    }

    private void fetchTeacherClasses(String username) {
        fetchTeacherName(username, name -> {
            if (name != null) {
                fetchClassesByTeacherName(name, teacherClasses -> {
                    if (teacherClasses != null) {
                        classes.clear();
                        classes.addAll(teacherClasses);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void fetchTeacherName(String username, OnCompleteListener<String> onComplete) {
        db.collection("teachers").whereEqualTo("username", username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        String name = task.getResult().getDocuments().get(0).getString("name");
                        onComplete.onComplete(name);
                    } else {
                        Log.w("Firestore", "No teacher found or error: ", task.getException());
                        onComplete.onComplete(null);
                    }
                });
    }

    private void fetchClassesByTeacherName(String name, OnCompleteListener<List<TeacherClass>> onComplete) {
        db.collection("courses_detail").whereEqualTo("classTeacher", name).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<TeacherClass> teacherClasses = new ArrayList<>();
                        for (QueryDocumentSnapshot courseDoc : task.getResult()) {
                            TeacherClass teacherClass = new TeacherClass(
                                    courseDoc.getString("classId"),
                                    courseDoc.getString("classSubject"),
                                    courseDoc.getString("studentNum"),
                                    courseDoc.getString("classTeacher")
                            );
                            teacherClasses.add(teacherClass);
                        }
                        onComplete.onComplete(teacherClasses);
                    } else {
                        Log.w("Firestore", "Error getting course details: ", task.getException());
                        onComplete.onComplete(null);
                    }
                });
    }

    private interface OnCompleteListener<T> {
        void onComplete(T result);
    }
}

