package com.example.chilltime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_NOTIFICATION = 0;
    private static final int TYPE_EXERCISE = 1;

    private List<Object> details;

    public DetailsAdapter(List<Object> details) {
        this.details = details;
    }

    @Override
    public int getItemViewType(int position) {
        if (details.get(position) instanceof Notification) {
            return TYPE_NOTIFICATION;
        } else {
            return TYPE_EXERCISE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NOTIFICATION) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_notification, parent, false);
            return new NotificationViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_exercise, parent, false);
            return new ExerciseViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationViewHolder) {
            Notification notification = (Notification) details.get(position);
            ((NotificationViewHolder) holder).bind(notification);
        } else {
            Exercise exercise = (Exercise) details.get(position);
            ((ExerciseViewHolder) holder).bind(exercise);
        }
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNotificationTitle);
            tvContent = itemView.findViewById(R.id.tvNotificationContent);
        }

        void bind(Notification notification) {
            tvTitle.setText(notification.getTitle());
            tvContent.setText(notification.getContent());
        }
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime, tvContent;

        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvExerciseTitle);
            tvTime = itemView.findViewById(R.id.tvExerciseTime);
            tvContent = itemView.findViewById(R.id.tvExerciseDocument);
        }

        void bind(Exercise exercise) {
            tvTitle.setText(exercise.getTitle());
            tvTime.setText(exercise.getTime());
            tvContent.setText(exercise.getContent());
        }
    }
}
