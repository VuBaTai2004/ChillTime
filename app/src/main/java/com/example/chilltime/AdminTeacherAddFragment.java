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

public class AdminTeacherAddFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_teacher_add, container, false);
        EditText et_id = view.findViewById(R.id.admin_et_teacher_id);
        EditText et_name = view.findViewById(R.id.admin_et_teacher_name);
        Button btn = view.findViewById(R.id.admin_btn_add_teacher);
        ImageView iv = view.findViewById(R.id.admin_iv_back_add);

        btn.setOnClickListener(view1 -> {

            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminTeacherFragment());
            fragmentTransaction.commit();
        });
        iv.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminTeacherFragment());
            fragmentTransaction.commit();
        });

        return view;
    }
}
