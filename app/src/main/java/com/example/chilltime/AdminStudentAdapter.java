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

public class AdminStudentAdapter extends RecyclerView.Adapter<AdminStudentAdapter.AdminStudentViewHolder> {
    private final Context context;
    private final ArrayList<AdminStudent> adminStudentArrayList;
    public AdminStudentAdapter(Context context, ArrayList<AdminStudent> studentArrayList){
        this.context = context;
        this.adminStudentArrayList = studentArrayList;
    }
    public static class AdminStudentViewHolder extends RecyclerView.ViewHolder {
        public TextView studentIdTextView;
        public TextView studentNameTextView;
        public ImageView imageModify;
        public ImageView imageDelete;
        // ... other views

        public AdminStudentViewHolder(View itemView) {
            super(itemView);
            studentIdTextView = itemView.findViewById(R.id.tv_student_id);
            studentNameTextView = itemView.findViewById(R.id.tv_student_name);
            imageModify = itemView.findViewById(R.id.iv_modify);
            imageDelete = itemView.findViewById(R.id.iv_delete);
            // ... initialize other views
        }
    }
    @NonNull
    @Override
    public AdminStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.admin_student_adapter, parent, false);
        return new AdminStudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminStudentViewHolder holder, int position) {
        AdminStudent currentItem = adminStudentArrayList.get(position);
        holder.studentIdTextView.setText(currentItem.getStudentId());
        holder.studentNameTextView.setText(currentItem.getStudentName());
        holder.imageModify.setOnClickListener(v -> {
            // Handle modify button click
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminStudentModifyFragment(currentItem));
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        holder.imageDelete.setOnClickListener(v -> {
            // Handle modify button click

        });
    }

    @Override
    public int getItemCount() {
        return adminStudentArrayList.size();
    }

}
