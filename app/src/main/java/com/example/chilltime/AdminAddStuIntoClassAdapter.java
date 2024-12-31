package com.example.chilltime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminAddStuIntoClassAdapter extends RecyclerView.Adapter<AdminAddStuIntoClassAdapter.ViewHolder> {
    private final List<StudentProfile> studentList;
    private final List<StudentProfile> selectedStudents; // Lưu danh sách học sinh được chọn

    public AdminAddStuIntoClassAdapter(List<StudentProfile> studentList) {
        this.studentList = studentList;
        this.selectedStudents = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_people_checkbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentProfile student = studentList.get(position);

        if (student != null) {
            holder.peopleNameTextView.setText(student.getName());
            holder.checkBox.setChecked(selectedStudents.contains(student));

            // Xử lý CheckBox
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!selectedStudents.contains(student)) {
                        selectedStudents.add(student);
                    }
                } else {
                    selectedStudents.remove(student);
                }
            });

            // Xử lý click item để chọn/bỏ chọn CheckBox
            holder.itemView.setOnClickListener(v -> {
                boolean isChecked = !holder.checkBox.isChecked();
                holder.checkBox.setChecked(isChecked);
            });
        }
    }

    @Override
    public int getItemCount() {
        return studentList != null ? studentList.size() : 0;
    }

    public List<StudentProfile> getSelectedStudents() {
        return selectedStudents;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView peopleNameTextView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            peopleNameTextView = itemView.findViewById(R.id.people_name);
            checkBox = itemView.findViewById(R.id.check_box);
        }
    }
}

