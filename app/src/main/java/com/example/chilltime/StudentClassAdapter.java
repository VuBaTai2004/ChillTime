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

public class StudentClassAdapter extends RecyclerView.Adapter<StudentClassAdapter.ClassViewHolder>{
    private final Context context;
    private final ArrayList<TeacherClass> classList;

    public StudentClassAdapter(Context context, ArrayList<TeacherClass> classList) {
        this.classList = classList;
        this.context = context;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView classIdTextView;
        public TextView classSubjectTextView;
        public TextView numStuTextView;
        public TextView classTeacherTextView;
        public Button openButton;

        public ClassViewHolder(View itemView) {
            super(itemView);
            classIdTextView = itemView.findViewById(R.id.course_code);
            classSubjectTextView = itemView.findViewById(R.id.course_title);
            numStuTextView = itemView.findViewById(R.id.number_stu);
            classTeacherTextView = itemView.findViewById(R.id.lecturer_name);
            openButton = itemView.findViewById(R.id.open_button);

        }

    }

    @Override
    public StudentClassAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.teacher_class_adapter, parent, false);
        return new StudentClassAdapter.ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentClassAdapter.ClassViewHolder holder, int position) {
        TeacherClass currentItem = classList.get(position);
        holder.classIdTextView.setText(currentItem.getClassId());
        holder.classSubjectTextView.setText(currentItem.getClassSubject());
        holder.numStuTextView.setText(String.valueOf(currentItem.getNumStu()));
        holder.classTeacherTextView.setText(currentItem.getClassTeacher());
        holder.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentOpenClass.class);

                intent.putExtra("classId", currentItem.getClassId());
                intent.putExtra("classSubject", currentItem.getClassSubject());
                intent.putExtra("numStu", currentItem.getNumStu());
                intent.putExtra("classTeacher", currentItem.getClassTeacher());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
