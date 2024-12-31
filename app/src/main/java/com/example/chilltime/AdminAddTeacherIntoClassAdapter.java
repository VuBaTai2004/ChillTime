package com.example.chilltime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminAddTeacherIntoClassAdapter extends RecyclerView.Adapter<AdminAddTeacherIntoClassAdapter.ViewHolder> {
    private final List<TeacherProfile> teacherList;
    private int selectedPosition = RecyclerView.NO_POSITION; // Để quản lý vị trí RadioButton được chọn

    public AdminAddTeacherIntoClassAdapter(List<TeacherProfile> teacherList) {
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout mới item_people_radio
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_people_radio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeacherProfile teacher = teacherList.get(position);

        // Kiểm tra teacher không null trước khi set dữ liệu
        if (teacher != null) {
            holder.peopleNameTextView.setText(teacher.getName());

            // Đặt trạng thái RadioButton
            holder.radioButton.setChecked(position == selectedPosition);

            // Xử lý sự kiện khi RadioButton được chọn
            holder.radioButton.setOnClickListener(v -> {
                notifyItemChanged(selectedPosition); // Làm mới mục trước đó
                selectedPosition = holder.getAdapterPosition(); // Cập nhật vị trí được chọn
                notifyItemChanged(selectedPosition); // Làm mới mục hiện tại
            });

            // Hoặc xử lý sự kiện click trên toàn bộ item
            holder.itemView.setOnClickListener(v -> {
                notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
            });
        }
    }

    @Override
    public int getItemCount() {
        return teacherList != null ? teacherList.size() : 0; // Tránh lỗi NullPointerException
    }

    // Trả về TeacherProfile được chọn
    public TeacherProfile getSelectedTeacher() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return teacherList.get(selectedPosition);
        }
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView peopleNameTextView;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ các thành phần trong layout item_people_radio
            peopleNameTextView = itemView.findViewById(R.id.people_name);
            radioButton = itemView.findViewById(R.id.radio_button);
        }
    }
}
