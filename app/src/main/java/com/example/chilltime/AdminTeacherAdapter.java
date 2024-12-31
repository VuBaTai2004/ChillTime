package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminTeacherAdapter extends RecyclerView.Adapter<AdminTeacherAdapter.ViewHolder> {

    private final ArrayList<TeacherProfile> teachers;
    private final Context context;

    public AdminTeacherAdapter (Context context, ArrayList<TeacherProfile> teachers) {
        this.teachers = teachers;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teacherNameTextView;
        public TextView teacherPhoneTextView;
        public TextView teacherEmailTextView;
        public TextView teacherCreatedAtTextView;
        public ImageView arrowIcon;
        ConstraintLayout itemPeople;
        public ViewHolder(View itemView) {
            super(itemView);
            teacherNameTextView = itemView.findViewById(R.id.people_name);
            arrowIcon = itemView.findViewById(R.id.arrow_icon1);
            itemPeople = itemView.findViewById(R.id.item_people_child);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_people, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeacherProfile currentItem = teachers.get(position);
        holder.teacherNameTextView.setText(currentItem.getName());
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
        holder.itemPeople.setOnClickListener(new View.OnClickListener() {
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
        return teachers.size();
    }

}
