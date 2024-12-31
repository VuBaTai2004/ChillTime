package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentNotificationAdapter extends RecyclerView.Adapter<StudentNotificationAdapter.NotificationViewHolder> {
    private List<Notification> studentNotifications;
    private Context context;

    public StudentNotificationAdapter(List<Notification> studentNotifications, Context context) {
        this.studentNotifications = studentNotifications;
        this.context = context;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvNotificationTitle);
            contentTextView = itemView.findViewById(R.id.tvNotificationContent);
        }
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification currentItem = studentNotifications.get(position);
        holder.titleTextView.setText(currentItem.getTitle());
        holder.contentTextView.setText(currentItem.getContent());

    }

    @Override
    public int getItemCount() {
        return studentNotifications.size();
    }
}
