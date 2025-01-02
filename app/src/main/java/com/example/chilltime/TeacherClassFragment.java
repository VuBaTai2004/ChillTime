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
        fetchTeacherId(username, teacherId -> {
            if (teacherId != null) {
                fetchClassIds(teacherId, classIds -> {
                    if (classIds != null) {
                        for (String classId : classIds) {
                            fetchClassDetails(classId, teacherClass -> {
                                if (teacherClass != null) {
                                    classes.add(teacherClass);
                                    adapter.filter("");
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
            }
        });
    }


    private void fetchTeacherId(String username, OnCompleteListener<String> onComplete) {
        db.collection("teachers").whereEqualTo("username", username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        String teacherId = task.getResult().getDocuments().get(0).getId();
                        onComplete.onComplete(teacherId);
                    } else {
                        Log.w("Firestore", "No teacher found or error: ", task.getException());
                        onComplete.onComplete(null);
                    }
                });
    }


    private void fetchClassIds(String teacherId, OnCompleteListener<List<String>> onComplete) {
        db.collection("teachers").document(teacherId).collection("class_list").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> classIds = new ArrayList<>();
                        for (QueryDocumentSnapshot classDoc : task.getResult()) {
                            String classId = classDoc.getString("classId");
                            if (classId != null) {
                                classIds.add(classId);
                            }
                        }
                        onComplete.onComplete(classIds);
                    } else {
                        Log.w("Firestore", "Error getting class list: ", task.getException());
                        onComplete.onComplete(null);
                    }
                });
    }

    private void fetchClassDetails(String classId, OnCompleteListener<TeacherClass> onComplete) {
        db.collection("courses_detail").document(classId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot courseDoc = task.getResult();
                        TeacherClass teacherClass = new TeacherClass(
                                courseDoc.getString("classId"),
                                courseDoc.getString("classSubject"),
                                courseDoc.getString("studentNum"),
                                courseDoc.getString("classTeacher")
                        );
                        onComplete.onComplete(teacherClass);
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
