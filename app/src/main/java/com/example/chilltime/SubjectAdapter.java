package com.example.chilltime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private List<Subject> subjects;

    public SubjectAdapter(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_noti, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);

        // Gán tên môn học
        holder.tvSubjectName.setText(subject.getName());

        // Kết hợp danh sách thông báo và bài tập
        List<Object> details = new ArrayList<>();
        details.addAll(subject.getNotifications());
        details.addAll(subject.getExercises());

        // Thiết lập RecyclerView bên trong
        DetailsAdapter detailsAdapter = new DetailsAdapter(details);
        holder.recyclerViewDetails.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewDetails.setAdapter(detailsAdapter);

        // Xử lý sự kiện click để mở/đóng RecyclerView
        holder.subjectLayout.setOnClickListener(v -> {
            if (holder.recyclerViewDetails.getVisibility() == View.GONE) {
                holder.recyclerViewDetails.setVisibility(View.VISIBLE);
                holder.ivArrow.setImageResource(R.drawable.keyboard_arrow_down); // Đổi mũi tên xuống
            } else {
                holder.recyclerViewDetails.setVisibility(View.GONE);
                holder.ivArrow.setImageResource(R.drawable.keyboard_arrow_right); // Đổi mũi tên phải
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName;
        ImageView ivArrow;
        LinearLayout subjectLayout;
        RecyclerView recyclerViewDetails;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            subjectLayout = itemView.findViewById(R.id.Subject);
            recyclerViewDetails = itemView.findViewById(R.id.recyclerViewDetails);
        }
    }
}
