package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminSubjectAdapter extends RecyclerView.Adapter<AdminSubjectAdapter.ViewHolder> {
    private final ArrayList<TeacherClass> subjects;
    private final Context context;

    public AdminSubjectAdapter(Context context, ArrayList<TeacherClass> subjects) {
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
        TeacherClass currentItem = subjects.get(position);
        holder.classIdTextView.setText(currentItem.getClassId());
        holder.classSubjectTextView.setText(currentItem.getClassSubject());
        holder.numStuTextView.setText(String.valueOf(currentItem.getNumStu()));
        holder.classTeacherTextView.setText(currentItem.getClassTeacher());
        holder.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle open button click event
            }
            });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
