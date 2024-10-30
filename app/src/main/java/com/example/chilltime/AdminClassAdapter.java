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
        public ImageView imageModify;
        public ImageView imageDelete;
        // ... other views

        public AdminClassViewHolder(View itemView) {
            super(itemView);
            classIdTextView = itemView.findViewById(R.id.tv_class_id);
            classSubjectTextView = itemView.findViewById(R.id.tv_class_subject);
            imageModify = itemView.findViewById(R.id.iv_modify);
            imageDelete = itemView.findViewById(R.id.iv_delete);
            // ... initialize other views
        }
    }

    @NonNull
    @Override
    public AdminClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.admin_class_adapter, parent, false);
        return new AdminClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminClassViewHolder holder, int position) {
        AdminClass currentItem = adminClassList.get(position);
        holder.classIdTextView.setText(currentItem.getClassId());
        holder.classSubjectTextView.setText(currentItem.getClassSubject());
        holder.imageModify.setOnClickListener(v -> {
            // Handle modify button click
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminClassModifyFragment(currentItem));
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        holder.imageDelete.setOnClickListener(v -> {
            // Handle modify button click
        });
    }

    @Override
    public int getItemCount() {
        return adminClassList.size();
    }

}
