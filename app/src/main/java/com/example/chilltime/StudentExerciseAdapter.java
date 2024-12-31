package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentExerciseAdapter extends RecyclerView.Adapter<StudentExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> studentExercises;
    private Context context;

    public StudentExerciseAdapter(List<Exercise> studentExercises, Context context) {
        this.studentExercises = studentExercises;
        this.context = context;
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
    public StudentExerciseAdapter.ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_exercise, parent, false);
        return new StudentExerciseAdapter.ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentExerciseAdapter.ExerciseViewHolder holder, int position) {
        Exercise currentItem = studentExercises.get(position);

        // Gán nội dung cho các TextView
        holder.titleTextView.setText(currentItem.getTitle());
        holder.timeTextView.setText(currentItem.getTime());
        holder.documentTextView.setText(currentItem.getContent());

        // Đặt sự kiện click cho toàn bộ item_exercise
        holder.itemExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang StudentExerciseDetail và truyền dữ liệu
                Intent intent = new Intent(context, StudentExerciseDetail.class);
                intent.putExtra("exerciseTitle", currentItem.getTitle());
                intent.putExtra("exerciseTime", currentItem.getTime());
                intent.putExtra("exerciseContent", currentItem.getContent());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentExercises.size();
    }

}
