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

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class StudentExerciseAdapter extends RecyclerView.Adapter<StudentExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> studentExercises;
    private Context context;
    private String classId;
    private String username;

    public StudentExerciseAdapter(List<Exercise> studentExercises, Context context, String classId, String username) {
        this.studentExercises = studentExercises;
        this.context = context;
        this.classId = classId;
        this.username = username;
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

        // Truy vấn Firestore để kiểm tra xem bài tập đã được nộp hay chưa
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("exercises")
                .whereEqualTo("classId", classId)
                .whereEqualTo("title", currentItem.getTitle())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        String exerciseDocId = queryDocumentSnapshots.getDocuments().get(0).getId();

                        // Kiểm tra trong sub-collection submit_homework
                        db.collection("exercises")
                                .document(exerciseDocId)
                                .collection("submit_homework")
                                .whereEqualTo("username", username)
                                .get()
                                .addOnSuccessListener(submitSnapshots -> {
                                    if (!submitSnapshots.isEmpty()) {
                                        // Nếu bài tập đã nộp, đổi màu titleTextView
                                        holder.titleTextView.setTextColor(context.getResources().getColor(R.color.colorDefault)); // Đổi sang mã màu #7073C9
                                    } else {
                                        // Nếu chưa nộp, để màu mặc định
                                        holder.titleTextView.setTextColor(context.getResources().getColor(R.color.colorSubmitted));
                                    }
                                });
                    }
                });

        // Đặt sự kiện click cho toàn bộ item_exercise
        holder.itemExercise.setOnClickListener(v -> {
            // Chuyển sang StudentExerciseDetail và truyền dữ liệu
            Intent intent = new Intent(context, StudentExerciseDetail.class);
            intent.putExtra("exerciseTitle", currentItem.getTitle());
            intent.putExtra("exerciseTime", currentItem.getTime());
            intent.putExtra("exerciseContent", currentItem.getContent());
            intent.putExtra("classId", classId);
            intent.putExtra("username", username);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return studentExercises.size();
    }

}
