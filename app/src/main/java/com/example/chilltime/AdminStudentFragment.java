
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
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

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

            EditText etSearch = view.findViewById(R.id.et_class_search);
            etSearch.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.filter(s.toString()); // Lọc danh sách khi người dùng nhập
                }

                @Override
                public void afterTextChanged(android.text.Editable s) {
                }
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
                            adapter.notifyDataSetChanged(); // Hiển thị danh sách ban đầu
                        } else {
                            Log.w("err", "Error getting documents.", task.getException());
                        }
                    });

            return view;
        }
    }