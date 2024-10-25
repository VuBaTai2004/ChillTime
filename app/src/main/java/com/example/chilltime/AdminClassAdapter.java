package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminClassAdapter extends RecyclerView.Adapter<AdminClassAdapter.AdminClassViewHolder> {
    private final Context context;
    private final ArrayList<AdminClass> adminClassList;

    public AdminClassAdapter(Context context, ArrayList<AdminClass> adminClassList) {
        this.adminClassList = adminClassList;
        this.context = context;
    }

    public static class AdminClassViewHolder extends RecyclerView.ViewHolder {
        public TextView classIdTextView;
        public TextView classSubjectTextView;
        // ... other views

        public AdminClassViewHolder(View itemView) {
            super(itemView);
            classIdTextView = itemView.findViewById(R.id.tv_class_id);
            classSubjectTextView = itemView.findViewById(R.id.tv_class_subject);
            // ... initialize other views
        }
    }

    @NonNull
    @Override
    public AdminClassAdapter.AdminClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.admin_class_adapter, parent, false);
        return new AdminClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminClassViewHolder holder, int position) {
        AdminClass currentItem = adminClassList.get(position);
        holder.classIdTextView.setText(currentItem.getClassId());
        holder.classSubjectTextView.setText(currentItem.getClassSubject());
    }

    @Override
    public int getItemCount() {
        return adminClassList.size();
    }
}
