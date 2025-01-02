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
    private final ArrayList<StudentProfile> originalStudents; // Danh sách gốc
    private final ArrayList<StudentProfile> filteredStudents; // Danh sách hiển thị
    private final Context context;

    public AdminStudentAdapter(Context context, ArrayList<StudentProfile> students) {
        this.originalStudents = new ArrayList<>(students); // Dữ liệu ban đầu từ Firebase
        this.filteredStudents = new ArrayList<>(students); // Dữ liệu để lọc
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentNameTextView;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_people, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentProfile currentItem = filteredStudents.get(position);
        holder.studentNameTextView.setText(currentItem.getName());

        holder.arrowIcon.setOnClickListener(v -> {
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
        return filteredStudents.size();
    }

    // Phương thức lọc danh sách
    public void filter(String query) {
        query = query.toLowerCase(Locale.getDefault()); // Không phân biệt chữ hoa/thường
        filteredStudents.clear();

        if (query.isEmpty()) {
            filteredStudents.addAll(originalStudents); // Hiển thị tất cả nếu ô tìm kiếm trống
        } else {
            for (StudentProfile student : originalStudents) {
                if (student.getName().toLowerCase(Locale.getDefault()).contains(query)) {
                    filteredStudents.add(student); // Thêm nếu chứa từ khóa
                }
            }
        }
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }
}