package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminTeacherAdapter extends RecyclerView.Adapter<AdminTeacherAdapter.AdminTeacherViewHolder>{
    private final Context context;
    private final ArrayList<AdminTeacher> adminTeacherArrayList;

    public AdminTeacherAdapter(Context context, ArrayList<AdminTeacher> adminTeacherArrayList) {
        this.adminTeacherArrayList = adminTeacherArrayList;
        this.context = context;
    }
    public static class AdminTeacherViewHolder extends RecyclerView.ViewHolder {
        public TextView teacherIdTextView;
        public TextView teacherNameTextView;
        public ImageView imageModify;
        public ImageView imageDelete;
        // ... other views

        public AdminTeacherViewHolder(View itemView) {
            super(itemView);
            teacherIdTextView = itemView.findViewById(R.id.tv_teacher_id);
            teacherNameTextView = itemView.findViewById(R.id.tv_teacher_name);
            imageModify = itemView.findViewById(R.id.iv_modify);
            imageDelete = itemView.findViewById(R.id.iv_delete);
            // ... initialize other views
        }
    }
    @NonNull
    @Override
    public AdminTeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.admin_teacher_adapter, parent, false);
        return new AdminTeacherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminTeacherViewHolder holder, int position) {
        AdminTeacher currentItem = adminTeacherArrayList.get(position);
        holder.teacherIdTextView.setText(currentItem.getTeacherId());
        holder.teacherNameTextView.setText(currentItem.getTeacherName());
        holder.imageModify.setOnClickListener(v -> {
            // Handle modify button click
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminTeacherModifyFragment(currentItem));
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        holder.imageDelete.setOnClickListener(v -> {
            // Handle modify button click

        });
    }

    @Override
    public int getItemCount() {
        return adminTeacherArrayList.size();
    }

}
