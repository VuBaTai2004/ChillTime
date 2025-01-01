package com.example.chilltime;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Timestamp;

public class StudentProfileFragment extends Fragment {
    private StudentProfile studentProfile;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        TextView tv_name = view.findViewById(R.id.text_name);
        TextView tv_id = view.findViewById(R.id.text_id);
        TextView tv_phone = view.findViewById(R.id.text_phone);
        TextView tv_email = view.findViewById(R.id.text_email);

        ImageView imageView = view.findViewById(R.id.profile_image);
        imageView.setImageResource(R.drawable.icon_student1);

        Button btn = view.findViewById(R.id.student_btn_logout);

        // Lấy dữ liệu username từ Bundle
        String username = getArguments() != null ? getArguments().getString("username") : null;

        if (username != null) {
            db.collection("students")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");
                                String id = document.getString("id");
                                String phone = document.getString("phone");
                                String email = document.getString("email");

                                // Hiển thị dữ liệu trên giao diện
                                tv_name.setText("Họ và tên: " + name);
                                tv_id.setText("Mã sinh viên: " + id);
                                tv_phone.setText("Số điện thoại: " + phone);
                                tv_email.setText("Email: " + email);
                            }
                        } else {
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                        }
                    });
        } else {
            Log.e("studentProfileFragment", "Username is null!");
        }

        btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        return view;
    }

}
