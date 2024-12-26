package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherExerciseAdapter extends RecyclerView.Adapter<TeacherExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> teacherExercises;
    private Context context;

    public TeacherExerciseAdapter(List<Exercise> teacherExercises, Context context) {
        this.teacherExercises = teacherExercises;
        this.context = context;
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView timeTextView;
        public TextView documentTextView;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvExerciseTitle);
            timeTextView = itemView.findViewById(R.id.tvExerciseTime);
            documentTextView = itemView.findViewById(R.id.tvExerciseDocument);
        }

    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_exercise, parent, false);
        return new TeacherExerciseAdapter.ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        Exercise currentItem = teacherExercises.get(position);
        holder.titleTextView.setText(currentItem.getTitle());
        holder.timeTextView.setText(currentItem.getTime());
        holder.documentTextView.setText(currentItem.getContent());
    }

    @Override
    public int getItemCount() {
        return teacherExercises.size();
    }

}
