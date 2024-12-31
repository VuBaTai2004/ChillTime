package com.example.chilltime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.List;

public class StudentTranscriptAdapter extends RecyclerView.Adapter<StudentTranscriptAdapter.ViewHolder> {
    private List<StudentTranscript> studentTranscripts;

    public StudentTranscriptAdapter(List<StudentTranscript> studentTranscripts){
        this.studentTranscripts=studentTranscripts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student_transcript_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        StudentTranscript studentTranscript = studentTranscripts.get(position);
        holder.classId.setText(studentTranscript.getClassId());
        holder.progressGrade.setText(String.valueOf(studentTranscript.getProgressGrade()));
        holder.praticeGrade.setText(String.valueOf(studentTranscript.getPracticeGrade()));
        holder.midtermGrade.setText(String.valueOf(studentTranscript.getMidtermGrade()));
        holder.termGrade.setText(String.valueOf(studentTranscript.getTermGrade()));
        holder.finalGrade.setText(String.valueOf(studentTranscript.getFinalGrade()));
    }

    public int getItemCount() {
        return studentTranscripts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView classId, credit, progressGrade, praticeGrade, midtermGrade, termGrade, finalGrade;

        public ViewHolder(View itemView) {
            super(itemView);
            classId = itemView.findViewById(R.id.tv_class_id);
            progressGrade = itemView.findViewById(R.id.tv_progress_grade);
            praticeGrade = itemView.findViewById(R.id.tv_practice_grade);
            midtermGrade = itemView.findViewById(R.id.tv_midterm_grade);
            termGrade = itemView.findViewById(R.id.tv_term_grade);
            finalGrade = itemView.findViewById(R.id.tv_final_grade);
        }
    }

}
