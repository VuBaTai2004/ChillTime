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
    private final ArrayList<StudentClass> classList;
    private ArrayList<StudentClass> filteredList;

    public StudentClassAdapter(Context context, ArrayList<StudentClass> classList) {
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
    public StudentClassAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.teacher_class_adapter, parent, false);
        return new StudentClassAdapter.ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentClassAdapter.ClassViewHolder holder, int position) {
        StudentClass currentItem = classList.get(position);
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
        return filteredList.size();
    }

    public void filter(String keyword) {
        filteredList.clear(); // Clear the filtered list

        if (keyword.isEmpty()) {
            // Nếu từ khóa rỗng, hiển thị tất cả item
            filteredList.addAll(classList);
        } else {
            // Lọc danh sách dựa trên từ khóa
            for (StudentClass item : classList) {
                // Kiểm tra null trước khi gọi toLowerCase()
                String classId = item.getClassId() != null ? item.getClassId().toLowerCase() : "";
                String classSubject = item.getClassSubject() != null ? item.getClassSubject().toLowerCase() : "";
                String classTeacher = item.getClassTeacher() != null ? item.getClassTeacher().toLowerCase() : "";
                String numStu = item.getNumStu() != null ? item.getNumStu().toLowerCase() : ""; // Nếu numStu là String
                // Kiểm tra xem từ khóa có khớp với bất kỳ trường nào không
                if (classId.contains(keyword.toLowerCase()) ||
                        classSubject.contains(keyword.toLowerCase()) ||
                        classTeacher.contains(keyword.toLowerCase()) ||
                        numStu.contains(keyword)) {
                    filteredList.add(item);
                }
            }
        }

        notifyDataSetChanged(); // Làm mới RecyclerView
    }


}
