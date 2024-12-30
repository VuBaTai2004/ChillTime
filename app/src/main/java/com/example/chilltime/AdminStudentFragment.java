package com.example.chilltime;

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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AdminStudentFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_student, container, false);
        ArrayList<AdminStudent> studentArrayList = new ArrayList<>();
        String[] spnStr = {"Tìm theo mã số", "Tìm theo tên"};
        RecyclerView rv = view.findViewById(R.id.rv_student_list);
        Spinner spn = view.findViewById(R.id.spn_student_search);
        Button btn = view.findViewById(R.id.btn_student_add);

        db.collection("students")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            AdminStudent student = new AdminStudent(document.getString("id").toString(), document.getString("name"),
                                    document.getString("email"), document.getString("phone"));
                            studentArrayList.add(student);
                        }
                        AdminStudentAdapter adapter = new AdminStudentAdapter(view.getContext(), studentArrayList);
                        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        rv.setAdapter(adapter);
                    } else {
                        Log.w("err", "Error getting documents.", task.getException());
                    }
                });

        AdminStudentAdapter adapter = new AdminStudentAdapter(view.getContext(), studentArrayList);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setAdapter(adapter);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, spnStr);
        spn.setAdapter(stringArrayAdapter);

        btn.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminStudentAddFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        return view;
    }
}