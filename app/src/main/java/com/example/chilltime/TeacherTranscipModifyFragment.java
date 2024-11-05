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

public class TeacherTranscipModifyFragment extends Fragment {
    private final TeacherTranscipt teacherTranscipt;
    public TeacherTranscipModifyFragment(TeacherTranscipt currentItem) {this.teacherTranscipt = currentItem;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_transcipt_modify, container, false);
        Button btn = view.findViewById(R.id.admin_btn_modify_class);
        ImageView iv = view.findViewById(R.id.admin_iv_back_modify);
        EditText et_id = view.findViewById(R.id.admin_et_student_id);
        EditText et_name = view.findViewById(R.id.admin_et_student_name);
        EditText et_process = view.findViewById(R.id.admin_et_student_process);
        EditText et_practice = view.findViewById(R.id.admin_et_student_practice);
        EditText et_midterm = view.findViewById(R.id.admin_et_student_midterm);
        EditText et_final = view.findViewById(R.id.admin_et_student_final);

        et_process.setText(teacherTranscipt.getStudentProcess().toString());
        et_practice.setText(teacherTranscipt.getStudentPratice().toString());
        et_midterm.setText(teacherTranscipt.getStudentMidterm().toString());
        et_final.setText(teacherTranscipt.getStudentFinal().toString());
        et_id.setText(teacherTranscipt.getStudentId().toString());
        et_name.setText(teacherTranscipt.getStudentName().toString());


        iv.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new TeacherTranscriptFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        btn.setOnClickListener(view2 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view2.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new TeacherTranscriptFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });



        return view;
    }
}
