package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherNotificationAdapter extends RecyclerView.Adapter<TeacherNotificationAdapter.NotificationViewHolder> {
    private List<Notification> teacherNotifications;
    private Context context;

    public TeacherNotificationAdapter(List<Notification> teacherNotifications, Context context) {
        this.teacherNotifications = teacherNotifications;
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
        return new TeacherNotificationAdapter.NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification currentItem = teacherNotifications.get(position);
        holder.titleTextView.setText(currentItem.getTitle());
        holder.contentTextView.setText(currentItem.getContent());

    } 

    @Override
    public int getItemCount() {
        return teacherNotifications.size();
    }
}
