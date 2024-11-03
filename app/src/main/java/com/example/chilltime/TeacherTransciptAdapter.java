package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherTransciptAdapter extends RecyclerView.Adapter<TeacherTransciptAdapter.TeacherTransciptViewHolder> {
    private final Context context;
    private ArrayList<TeacherTranscipt> teacherTransciptList;
    private ArrayList<TeacherTranscipt> filteredList;

    public TeacherTransciptAdapter(Context context, ArrayList<TeacherTranscipt> teacherTransciptList) {
        this.teacherTransciptList = teacherTransciptList;
        this.context = context;
        this.filteredList = new ArrayList<>(teacherTransciptList); // Bắt đầu với danh sách đầy đủ
    }

    public static class TeacherTransciptViewHolder extends RecyclerView.ViewHolder {
        public TextView studentIdTextView;
        public TextView studentNameTextView;
        public TextView studentProcessTextView;
        public TextView studentPraticeTextView;
        public TextView studentMidtermTextView;
        public TextView studentFinalTextView;
        public TextView studentTotalTextView;
        public ImageView editButton;

        public TeacherTransciptViewHolder(View itemView) {
            super(itemView);
            studentIdTextView = itemView.findViewById(R.id.tv_student_id);
            studentNameTextView = itemView.findViewById(R.id.tv_student_name);
            studentProcessTextView = itemView.findViewById(R.id.tv_process_point);
            studentPraticeTextView = itemView.findViewById(R.id.tv_practice_point);
            studentMidtermTextView = itemView.findViewById(R.id.tv_midterm_point);
            studentFinalTextView = itemView.findViewById(R.id.tv_final_point);
            studentTotalTextView = itemView.findViewById(R.id.tv_total_point);
            editButton = itemView.findViewById(R.id.iv_modify);
        }
    }

    @NonNull
    @Override
    public TeacherTransciptAdapter.TeacherTransciptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.teacher_transcipt_adapter, parent, false);
        return new TeacherTransciptViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeacherTransciptAdapter.TeacherTransciptViewHolder holder, int position) {
        TeacherTranscipt currentItem = filteredList.get(position);
        holder.studentIdTextView.setText(currentItem.getStudentId());
        holder.studentNameTextView.setText(currentItem.getStudentName());
        holder.studentProcessTextView.setText(currentItem.getStudentProcess());
        holder.studentPraticeTextView.setText(currentItem.getStudentPratice());
        holder.studentMidtermTextView.setText(currentItem.getStudentMidterm());
        holder.studentFinalTextView.setText(currentItem.getStudentFinal());
        holder.studentTotalTextView.setText(currentItem.getStudentTotal());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new TeacherTranscipModifyFragment(currentItem));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size(); // Sử dụng danh sách đã lọc
    }

    // Hàm này được gọi từ TeacherTranscriptFragment để cập nhật danh sách hiển thị
    public void setFilteredList(ArrayList<TeacherTranscipt> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged(); // Thông báo RecyclerView cập nhật lại giao diện
    }
}
