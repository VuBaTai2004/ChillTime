package com.example.chilltime;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AdminStudentModifyFragment extends Fragment {
    protected final AdminStudent adminStudent;
    public AdminStudentModifyFragment(AdminStudent currentItem) {
        this.adminStudent = currentItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_student_modify, container, false);
        Button btn = view.findViewById(R.id.admin_btn_modify_student);
        ImageView iv = view.findViewById(R.id.admin_iv_back_modify);
        EditText et_id = view.findViewById(R.id.admin_et_student_id);
        EditText et_name = view.findViewById(R.id.admin_et_student_name);
        EditText et_mail = view.findViewById(R.id.admin_et_student_mail);
        EditText et_phone = view.findViewById(R.id.admin_et_student_phone);

        et_id.setText(adminStudent.getStudentId().toString());
        et_name.setText(adminStudent.getStudentName().toString());
        et_mail.setText(adminStudent.getStudentEmail().toString());
        et_phone.setText(adminStudent.getStudentPhone().toString());
        iv.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminStudentFragment());
            fragmentTransaction.commit();
        });
        btn.setOnClickListener(view2 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view2.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminStudentFragment());
            fragmentTransaction.commit();
        });

        return view;
    }
}
