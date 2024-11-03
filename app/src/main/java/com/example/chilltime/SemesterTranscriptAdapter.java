package com.example.chilltime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SemesterTranscriptAdapter extends RecyclerView.Adapter<SemesterTranscriptAdapter.ViewHolder> {
    private List<List<StudentTranscript>> semesters;

    public SemesterTranscriptAdapter(List<List<StudentTranscript>> semesters) {
        this.semesters = semesters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student_transcript_title, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<StudentTranscript> semester = semesters.get(position);
        StudentTranscriptAdapter studentTranscriptAdapter = new StudentTranscriptAdapter(semester);
        holder.rvStudentTranscript.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvStudentTranscript.setAdapter(studentTranscriptAdapter);

        // Cập nhật tên học kỳ
        holder.tvStudentSemester.setText(semester.get(0).getSemester());
    }

    @Override
    public int getItemCount() {
        return semesters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentSemester;
        RecyclerView rvStudentTranscript;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStudentSemester = itemView.findViewById(R.id.tv_student_semester);
            rvStudentTranscript = itemView.findViewById(R.id.rv_table_grade);
        }
    }
}
