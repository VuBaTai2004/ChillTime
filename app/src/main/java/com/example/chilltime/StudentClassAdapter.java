package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentClassAdapter extends RecyclerView.Adapter<StudentClassAdapter.StudentClassViewHolder> {

    private final Context context;
    private List<StudentClass> studentClassList;

    public StudentClassAdapter(Context context, List<StudentClass> studentClassList) {
        this.studentClassList = studentClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.student_class_adapter, parent, false);
        return new StudentClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentClassViewHolder holder, int position) {
        StudentClass studentClass = studentClassList.get(position);
        holder.classId.setText(studentClass.getClassId());
        holder.className.setText(studentClass.getClassName());
        holder.startTime.setText(studentClass.getStartTime());
        holder.endTime.setText(studentClass.getEndTime());
    }

    @Override
    public int getItemCount() {
        return studentClassList.size();
    }

    static class StudentClassViewHolder extends RecyclerView.ViewHolder {
        TextView classId, className, startTime, endTime;

        StudentClassViewHolder(@NonNull View itemView) {
            super(itemView);
            classId = itemView.findViewById(R.id.classId);
            className = itemView.findViewById(R.id.classNameTextView);
            startTime = itemView.findViewById(R.id.startTimeTextView);
            endTime = itemView.findViewById(R.id.endTimeTextView);
        }
    }
}
