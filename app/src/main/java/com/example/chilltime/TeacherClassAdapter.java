package com.example.chilltime;

import static androidx.core.content.ContextCompat.startActivity;

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

public class TeacherClassAdapter extends RecyclerView.Adapter<TeacherClassAdapter.ClassViewHolder> {
    private final Context context;
    private final ArrayList<TeacherClass> classList; // Original list
    private ArrayList<TeacherClass> filteredList;    // Filtered list

    public TeacherClassAdapter(Context context, ArrayList<TeacherClass> classList) {
        this.classList = classList;
        this.filteredList = new ArrayList<>(classList);
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
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.teacher_class_adapter, parent, false);
        return new ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        TeacherClass currentItem = classList.get(position);
        holder.classIdTextView.setText(currentItem.getClassId());
        holder.classSubjectTextView.setText(currentItem.getClassSubject());
        holder.numStuTextView.setText(String.valueOf(currentItem.getNumStu()));
        holder.classTeacherTextView.setText(currentItem.getClassTeacher());
        holder.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeacherOpenClass.class);

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
        return filteredList.size();
    }

    public void filter(String keyword) {
        filteredList.clear(); // Clear the filtered list
        if (keyword.isEmpty()) {
            // If no keyword, show all items
            filteredList.addAll(classList);
        } else {
            // Filter items based on the keyword
            for (TeacherClass item : classList) {
                // Check if keyword matches any field (case insensitive)
                if (item.getClassId().toLowerCase().contains(keyword.toLowerCase()) ||
                        item.getClassSubject().toLowerCase().contains(keyword.toLowerCase()) ||
                        item.getClassTeacher().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged(); // Refresh RecyclerView
    }
}
