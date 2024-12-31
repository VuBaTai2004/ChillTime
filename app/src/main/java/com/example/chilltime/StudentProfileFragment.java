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

import java.sql.Timestamp;

public class StudentProfileFragment extends Fragment {
    private StudentProfile studentProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        studentProfile = new StudentProfile("Pham Minh E","120", "0868480060", "quanpham0405@gmail.com");

        TextView tv_name = view.findViewById(R.id.text_name);
        TextView tv_id = view.findViewById(R.id.text_id);
        TextView tv_phone = view.findViewById(R.id.text_phone);
        TextView tv_email = view.findViewById(R.id.text_email);


        tv_name.setText("Họ và tên: " + studentProfile.getName());
        tv_id.setText("Mã học viên: " + studentProfile.getId());
        tv_phone.setText("Số điện thoại: " + studentProfile.getPhone());
        tv_email.setText("Email: " + studentProfile.getEmail());

        ImageView imageView = view.findViewById(R.id.profile_image);
        imageView.setImageResource(R.drawable.icon_student1);

        Button btn = view.findViewById(R.id.student_btn_logout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        return view;
    }
}