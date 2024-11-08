package com.example.chilltime;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_student_profile, container, false);

        StudentProfile studentProfile = new StudentProfile("Vu Ba F", "vubatai", "02131832", "abcdxyz@gmail.com");

        TextView tv_name = itemView.findViewById(R.id.text_name);
        TextView tv_user = itemView.findViewById(R.id.text_username);
        TextView tv_phone = itemView.findViewById(R.id.text_phone);
        TextView tv_email = itemView.findViewById(R.id.text_email);

        tv_name.setText(studentProfile.getName());
        tv_user.setText(studentProfile.getUsername());
        tv_phone.setText(studentProfile.getPhone());
        tv_email.setText(studentProfile.getEmail());

        ImageView imageView = itemView.findViewById(R.id.profile_image);
        imageView.setImageResource(R.drawable.icon_student1);


        Button btn = itemView.findViewById(R.id.student_btn_logout);
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        return itemView;
    }
}