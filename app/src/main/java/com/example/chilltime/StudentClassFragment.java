package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentClassFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<StudentClass> classes = new ArrayList<>();
    private StudentClassAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_class, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_class_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new StudentClassAdapter(view.getContext(), classes);
        recyclerView.setAdapter(adapter);

        // Fetch data from Firestore
        String username = getArguments() != null ? getArguments().getString("username") : null;
        if (username != null) {
            fetchStudentClasses(username);
        } else {
            Log.e("StudentClassFragment", "Username is null, cannot load classes.");
        }

        EditText etSearch = view.findViewById(R.id.et_class_search);
        etSearch.setText("");
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần làm gì
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Gọi hàm filter() của adapter
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                // Không cần làm gì
            }
        });


        return view;
    }
    private void fetchStudentClasses(String username) {
        fetchStudentId(username, studentId -> {
            if (studentId != null) {
                fetchClassIds(studentId, classIds -> {
                    if (classIds != null) {
                        for (String classId : classIds) {
                            fetchClassDetails(classId, studentClass -> {
                                if (studentClass != null) {
                                    classes.add(studentClass);
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


    private void fetchStudentId(String username, StudentClassFragment.OnCompleteListener<String> onComplete) {
        db.collection("students").whereEqualTo("username", username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        String studentId = task.getResult().getDocuments().get(0).getId();
                        onComplete.onComplete(studentId);
                    } else {
                        Log.w("Firestore", "No student found or error: ", task.getException());
                        onComplete.onComplete(null);
                    }
                });
    }


    private void fetchClassIds(String studentId, StudentClassFragment.OnCompleteListener<List<String>> onComplete) {
        db.collection("students").document(studentId).collection("class_list").get()
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

    private void fetchClassDetails(String classId, StudentClassFragment.OnCompleteListener<StudentClass> onComplete) {
        db.collection("courses_detail").document(classId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot courseDoc = task.getResult();
                        StudentClass studentClass = new StudentClass(
                                courseDoc.getString("classId"),
                                courseDoc.getString("classSubject"),
                                courseDoc.getString("studentNum"),
                                courseDoc.getString("classTeacher")
                        );
                        onComplete.onComplete(studentClass);
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