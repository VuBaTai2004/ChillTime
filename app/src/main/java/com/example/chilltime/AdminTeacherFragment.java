package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

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

        EditText etClassSearch = view.findViewById(R.id.et_class_search);


        ArrayList<TeacherProfile> teachers = new ArrayList<>();
        AdminTeacherAdapter adapter = new AdminTeacherAdapter(getContext(), teachers);
        recyclerView.setAdapter(adapter);
        defaultTeacherList(teachers, adapter);

        teachers.add(new TeacherProfile("Pham Minh E","120", "0868480060", "quanpham0405@gmail.com"));

        FloatingActionButton add = view.findViewById(R.id.add);
        add.setOnClickListener(v -> {
            // Handle add button click event
            Intent intent = new Intent(getContext(), AdminAddTeacher.class);
            startActivity(intent);
        });

        db.collection("teachers").orderBy("id")
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
//                                Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    ArrayList<StudentProfile> cities = new ArrayList<>();
                    teachers.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        if (doc.get("name") != null) {
                            String name = doc.getString("name");
                            String id = doc.getString("id");
                            String phone = doc.getString("phone");
                            String email = doc.getString("email");
                            TeacherProfile student = new TeacherProfile(name, id, phone, email);
                            teachers.add(student);
                        }
                    }
                    adapter.notifyDataSetChanged();
//                        Log.d(TAG, "Current cites in CA: " + cities);
                });

        etClassSearch.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Xử lý khi nhấn Enter
                String input = etClassSearch.getText().toString();
                recyclerView.setAdapter(adapter);
                db.collection("teachers").orderBy("id")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                teachers.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.getString("name").toLowerCase().contains(input.toLowerCase())){
                                        TeacherProfile student = new TeacherProfile(
                                                document.getString("name"),
                                                document.getString("id"),  // Thêm trường id
                                                document.getString("phone"),
                                                document.getString("email")
                                        );
                                        teachers.add(student);
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
        adapter.notifyDataSetChanged();
        return view;
    }
    public void defaultTeacherList(ArrayList<TeacherProfile> teachers, AdminTeacherAdapter adapter){
        db.collection("teachers").orderBy("id")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        teachers.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            TeacherProfile student = new TeacherProfile(
                                    document.getString("name"),
                                    document.getString("id"),  // Thêm trường id
                                    document.getString("phone"),
                                    document.getString("email")
                            );
                            teachers.add(student);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("err", "Error getting documents.", task.getException());
                    }
                });
    }
}