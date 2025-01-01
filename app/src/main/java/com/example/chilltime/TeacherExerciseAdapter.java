package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherExerciseAdapter extends RecyclerView.Adapter<TeacherExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> teacherExercises;
    private Context context;
    private String classId;

    public TeacherExerciseAdapter(List<Exercise> teacherExercises, Context context, String classId) {
        this.teacherExercises = teacherExercises;
        this.context = context;
        this.classId = classId;
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView timeTextView;
        public TextView documentTextView;
        public LinearLayout itemExercise;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvExerciseTitle);
            timeTextView = itemView.findViewById(R.id.tvExerciseTime);
            documentTextView = itemView.findViewById(R.id.tvExerciseDocument);
            itemExercise = itemView.findViewById(R.id.item_exercise_child);
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
        holder.itemExercise.setOnClickListener(v -> {
            // Xử lý khi item được nhấn
            Intent intent = new Intent(context, TeacherExerciseInfo.class);
            intent.putExtra("exerciseTitle", currentItem.getTitle());
            intent.putExtra("exerciseTime", currentItem.getTime());
            intent.putExtra("exerciseContent", currentItem.getContent());
            intent.putExtra("classId", classId);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return teacherExercises.size();
    }

}
