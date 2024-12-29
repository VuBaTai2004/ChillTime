package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminStudentAdapter extends RecyclerView.Adapter<AdminStudentAdapter.ViewHolder> {
    private final ArrayList<TeacherProfile> students;
    private final Context context;

    public AdminStudentAdapter(Context context, ArrayList<TeacherProfile> students) {
        this.students = students;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentNameTextView;
        public TextView studentPhoneTextView;
        public TextView studentEmailTextView;
        public TextView studentCreatedAtTextView;
        public ImageView arrowIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.people_name);
            arrowIcon = itemView.findViewById(R.id.arrow_icon1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_people, parent, false);
        return new AdminStudentAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeacherProfile currentItem = students.get(position);
        holder.studentNameTextView.setText(currentItem.getName());
        holder.arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle arrow icon click event
                Intent intent = new Intent(context, AdminTeacherInfo.class);
                intent.putExtra("teacherName", currentItem.getName());
                intent.putExtra("teacherPhone", currentItem.getPhone());
                intent.putExtra("teacherEmail", currentItem.getEmail());
                intent.putExtra("teacherCreatedAt", currentItem.getCreatedAt());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }



}
