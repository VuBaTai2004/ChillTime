
    package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

    public class AdminStudentFragment extends Fragment {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_admin_student, container, false);

            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            ArrayList<StudentProfile> students = new ArrayList<>();
            AdminStudentAdapter adapter = new AdminStudentAdapter(getContext(), students);
            recyclerView.setAdapter(adapter);

            FloatingActionButton add = view.findViewById(R.id.add);
            add.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), AdminAddStudent.class);
                startActivity(intent);
            });

            db.collection("students")
                    .addSnapshotListener((value, e) -> {
                        if (e != null) {
//                                Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        ArrayList<StudentProfile> cities = new ArrayList<>();
                        students.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("name") != null) {
                                String name = doc.getString("name");
                                String id = doc.getString("id");
                                String phone = doc.getString("phone");
                                String email = doc.getString("email");
                                StudentProfile student = new StudentProfile(name, id, phone, email);
                                students.add(student);
                            }
                        }
                        adapter.notifyDataSetChanged();
//                        Log.d(TAG, "Current cites in CA: " + cities);
                    });

            EditText etClassSearch = view.findViewById(R.id.et_class_search);
            etClassSearch.setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Xử lý khi nhấn Enter
                    String input = etClassSearch.getText().toString();
                    recyclerView.setAdapter(adapter);
                    db.collection("students").orderBy("id")
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    students.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("name").toLowerCase().contains(input.toLowerCase())){
                                            StudentProfile student = new StudentProfile(
                                                    document.getString("name"),
                                                    document.getString("id"),  // Thêm trường id
                                                    document.getString("phone"),
                                                    document.getString("email")
                                            );
                                            students.add(student);
                                        }

                                    }
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Log.w("err", "Error getting documents.", task.getException());
                                }
                            });

                    // Làm gì đó với input

                    return true; // Đã xử lý sự kiện
                }
                return false; // Không xử lý
            });

            db.collection("students").orderBy("id")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            students.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                StudentProfile student = new StudentProfile(
                                        document.getString("name"),
                                        document.getString("id"),
                                        document.getString("phone"),
                                        document.getString("email")
                                );
                                students.add(student);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w("err", "Error getting documents.", task.getException());
                        }
                    });

            return view;
        }
    }