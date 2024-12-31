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

public class AdminOpenClassAdapter extends RecyclerView.Adapter<AdminOpenClassAdapter.ViewHolder> {
    private final ArrayList<StudentProfile> students;
    private final Context context;

    public AdminOpenClassAdapter(Context context, ArrayList<StudentProfile> students) {
        this.students = students;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentNameTextView;
        public TextView studentIdTextView;
        public TextView studentPhoneTextView;
        public TextView studentEmailTextView;
        public ImageView arrowIcon;
        ConstraintLayout itemPeople;

        public ViewHolder(View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.people_name);
            arrowIcon = itemView.findViewById(R.id.arrow_icon1);
            itemPeople = itemView.findViewById(R.id.item_people_child);
        }
    }

    @Override
    public AdminOpenClassAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_people, parent, false);
        return new AdminOpenClassAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(AdminOpenClassAdapter.ViewHolder holder, int position) {
        StudentProfile currentItem = students.get(position);
        holder.studentNameTextView.setText(currentItem.getName());
        holder.arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle arrow icon click event
                Intent intent = new Intent(context, AdminStudentInClass.class);
                intent.putExtra("studentName", currentItem.getName());
                intent.putExtra("studentId", currentItem.getId());
                intent.putExtra("studentPhone", currentItem.getPhone());
                intent.putExtra("studentEmail", currentItem.getEmail());
                context.startActivity(intent);
            }
        });
        holder.itemPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle arrow icon click event
                Intent intent = new Intent(context, AdminStudentInClass.class);
                intent.putExtra("studentName", currentItem.getName());
                intent.putExtra("studentId", currentItem.getId());
                intent.putExtra("studentPhone", currentItem.getPhone());
                intent.putExtra("studentEmail", currentItem.getEmail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


}
