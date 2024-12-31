package com.example.chilltime;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.sql.Timestamp;

public class TeacherProfileFragment extends Fragment {
    private TeacherProfile teacherProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        teacherProfile = new TeacherProfile("Pham Minh E", "120","0868480060", "quanpham0405@gmail.com");

        TextView tv_name = view.findViewById(R.id.text_name);
        TextView tv_id = view.findViewById(R.id.text_id);
        TextView tv_phone = view.findViewById(R.id.text_phone);
        TextView tv_email = view.findViewById(R.id.text_email);


        tv_name.setText("Họ và tên: " + teacherProfile.getName());
        tv_id.setText("Mã giảng viên: " + teacherProfile.getId());
        tv_phone.setText("Số điện thoại: " + teacherProfile.getPhone());
        tv_email.setText("Email: " + teacherProfile.getEmail());

        ImageView imageView = view.findViewById(R.id.profile_image);
        imageView.setImageResource(R.drawable.icon_teacher1);

        Button btn = view.findViewById(R.id.teacher_btn_logout);


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
