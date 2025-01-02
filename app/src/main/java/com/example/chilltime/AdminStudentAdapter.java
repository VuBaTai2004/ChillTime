package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class AdminStudentAdapter extends RecyclerView.Adapter<AdminStudentAdapter.ViewHolder> {
    private final ArrayList<StudentProfile> students;
    private final Context context;

    public AdminStudentAdapter(Context context, ArrayList<StudentProfile> students) {
        this.students = students;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentNameTextView;
        public ImageView arrowIcon;
        public ImageView editIcon;
        ConstraintLayout itemPeople;

        public ViewHolder(View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.people_name);
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
        StudentProfile currentItem = students.get(position);
        holder.studentNameTextView.setText(currentItem.getName());

        holder.arrowIcon.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminStudentInfo.class);
            intent.putExtra("studentName", currentItem.getName());
            intent.putExtra("studentId", currentItem.getId());
            intent.putExtra("studentPhone", currentItem.getPhone());
            intent.putExtra("studentEmail", currentItem.getEmail());
            context.startActivity(intent);
        });

        holder.itemPeople.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminStudentInfo.class);
            intent.putExtra("studentName", currentItem.getName());
            intent.putExtra("studentId", currentItem.getId());
            intent.putExtra("studentPhone", currentItem.getPhone());
            intent.putExtra("studentEmail", currentItem.getEmail());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

}