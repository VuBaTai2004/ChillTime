package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminSubjectAdapter extends RecyclerView.Adapter<AdminSubjectAdapter.ViewHolder> {
    private final ArrayList<StudentClass> subjects;
    private final Context context;

    public AdminSubjectAdapter(Context context, ArrayList<StudentClass> subjects) {
        this.subjects = subjects;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView classIdTextView;
        public TextView classSubjectTextView;
        public TextView numStuTextView;
        public TextView classTeacherTextView;
        public Button openButton;

        public ViewHolder(View itemView) {
            super(itemView);
            classIdTextView = itemView.findViewById(R.id.course_code);
            classSubjectTextView = itemView.findViewById(R.id.course_title);
            numStuTextView = itemView.findViewById(R.id.number_stu);
            classTeacherTextView = itemView.findViewById(R.id.lecturer_name);
            openButton = itemView.findViewById(R.id.open_button);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.teacher_class_adapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentClass currentItem = subjects.get(position);
        holder.classIdTextView.setText(currentItem.getClassId());
        holder.classSubjectTextView.setText(currentItem.getClassSubject());
        holder.numStuTextView.setText(String.valueOf(currentItem.getNumStu()));
        holder.classTeacherTextView.setText(currentItem.getClassTeacher());

        // Ẩn TextView `lecturer_name` và `number_stu`
        holder.numStuTextView.setVisibility(View.INVISIBLE);  // Hoặc View.INVISIBLE
        holder.classTeacherTextView.setVisibility(View.INVISIBLE);  // Hoặc View.INVISIBLE

        holder.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle open button click event
                Intent intent = new Intent(context, AdminClass.class);
                intent.putExtra("classId", currentItem.getClassId());
                intent.putExtra("classSubject", currentItem.getClassSubject());
                context.startActivity(intent);
            }
            });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
