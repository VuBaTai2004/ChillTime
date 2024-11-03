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

public class AdminStudentAddFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_student_add, container, false);
        Button btn = view.findViewById(R.id.admin_btn_add_student);
        ImageView iv = view.findViewById(R.id.admin_iv_back_add);
        EditText et_id = view.findViewById(R.id.admin_et_student_id);
        EditText et_name = view.findViewById(R.id.admin_et_student_name);
        EditText et_mail = view.findViewById(R.id.admin_et_student_mail);
        EditText et_phone = view.findViewById(R.id.admin_et_student_phone);
        String id = et_id.getText().toString();
        String name = et_name.getText().toString();
        String email = et_mail.getText().toString();
        String phone = et_phone.getText().toString();
        AdminStudent student = new AdminStudent(id,name,email,phone);

        iv.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminTeacherFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        btn.setOnClickListener(view2 -> {
            addClassToDB(student);
            FragmentManager fragmentManager = ((AppCompatActivity) view2.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminTeacherFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });

        return view;
    }

    public void addClassToDB(AdminStudent student){

    }
}
