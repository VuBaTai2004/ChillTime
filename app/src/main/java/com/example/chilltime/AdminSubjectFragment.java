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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AdminSubjectFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_subject, container,false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArrayList<StudentClass> subjects = new ArrayList<>();
        AdminSubjectAdapter adapter = new AdminSubjectAdapter(getContext(), subjects);
        recyclerView.setAdapter(adapter);


        subjects.add(new StudentClass("NT131.P13", "Hệ thống Nhúng mạng không dây", "10", "Nguyễn Văn A"));
        subjects.add(new StudentClass("NT532.P11", "Công nghệ Internet of things hiện đại",  "10", "Nguyễn Văn B"));


        db.collection("courses").orderBy("id")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        subjects.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            StudentClass student = new StudentClass(document.getString("id"), document.getString("subject"),
                                    "70", "");
                            subjects.add(student);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("err", "Error getting documents.", task.getException());
                    }
                });

        EditText etClassSearch = view.findViewById(R.id.et_class_search);
        etClassSearch.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Xử lý khi nhấn Enter
                String input = etClassSearch.getText().toString();

                db.collection("courses").orderBy("id")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                subjects.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.getString("subject").toLowerCase().contains(input.toLowerCase())
                                        || document.getString("id").toLowerCase().contains(input.toLowerCase())){
                                        StudentClass student = new StudentClass(document.getString("id"), document.getString("subject"),
                                                "70", "");
                                        subjects.add(student);
                                    }

                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.w("err", "Error getting documents.", task.getException());
                            }
                        });

                return true; // Đã xử lý sự kiện
            }
            return false; // Không xử lý
        });

        FloatingActionButton add = view.findViewById(R.id.add);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AdminAddSubject.class);
            startActivity(intent);
        });
        adapter.notifyDataSetChanged();
        return view;
    }

}