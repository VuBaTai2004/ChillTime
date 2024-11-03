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
    private List<StudentSemesterTranscript> semesters;


    public SemesterTranscriptAdapter(List<StudentSemesterTranscript> semesters) {
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
        StudentSemesterTranscript semester = semesters.get(position);
        holder.tvStudentSemester.setText(semester.getCurrentSemester());

        StudentTranscriptAdapter studentTranscriptAdapter = new StudentTranscriptAdapter(semester.getStudentTranscripts());
        holder.rvStudentTranscript.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvStudentTranscript.setAdapter(studentTranscriptAdapter);
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
