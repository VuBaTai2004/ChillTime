package com.example.chilltime;

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

public class AdminTeacherModifyFragment extends Fragment {
    protected final AdminTeacher adminTeacher;
    public AdminTeacherModifyFragment(AdminTeacher currentItem) {
        this.adminTeacher = currentItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_teacher_modify, container, false);
        Button btn = view.findViewById(R.id.admin_btn_modify_teacher);
        ImageView iv = view.findViewById(R.id.admin_iv_back_modify);
        EditText et_id = view.findViewById(R.id.admin_et_teacher_id);
        EditText et_name = view.findViewById(R.id.admin_et_teacher_name);
        EditText et_mail = view.findViewById(R.id.admin_et_teacher_mail);
        EditText et_phone = view.findViewById(R.id.admin_et_teacher_phone);

        et_id.setText(adminTeacher.getTeacherId().toString());
        et_name.setText(adminTeacher.getTeacherName().toString());
        et_mail.setText(adminTeacher.getTeacherEmail().toString());
        et_phone.setText(adminTeacher.getTeacherPhone().toString());
        iv.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminTeacherFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        btn.setOnClickListener(view2 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view2.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminTeacherFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });

        return view;
    }
}
