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

public class TeacherNotificationAdapter extends RecyclerView.Adapter<TeacherNotificationAdapter.NotificationViewHolder> {
    private List<Notification> teacherNotifications;
    private Context context;
    private String classId;


    public TeacherNotificationAdapter(List<Notification> teacherNotifications, Context context, String classId) {
        this.teacherNotifications = teacherNotifications;
        this.context = context;
        this.classId = classId;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;
        public LinearLayout itemNotification;


        public NotificationViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvNotificationTitle);
            contentTextView = itemView.findViewById(R.id.tvNotificationContent);
            itemNotification = itemView.findViewById(R.id.item_notification_child);
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
        holder.itemNotification.setOnClickListener(v -> {
            Intent intent = new Intent(context, TeacherNotificationInfo.class);
            intent.putExtra("exerciseTitle", currentItem.getTitle());
            intent.putExtra("exerciseContent", currentItem.getContent());
            intent.putExtra("classId", classId);
            context.startActivity(intent);
        });

    } 

    @Override
    public int getItemCount() {
        return teacherNotifications.size();
    }
}
