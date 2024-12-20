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
    private ArrayList<AdminClass> filteredList;

    public AdminClassAdapter(Context context, ArrayList<AdminClass> adminClassList) {
        this.adminClassList = adminClassList;
        this.context = context;
        this.filteredList = new ArrayList<>(adminClassList);
    }

    public static class AdminClassViewHolder extends RecyclerView.ViewHolder {
        public TextView classIdTextView;
        public TextView classSubjectTextView;
        public ImageView editButton;
        public ImageView deleteButton;
        // ... other views

        public AdminClassViewHolder(View itemView) {
            super(itemView);
            classIdTextView = itemView.findViewById(R.id.tv_class_id);
            classSubjectTextView = itemView.findViewById(R.id.tv_class_subject);
            editButton = itemView.findViewById(R.id.iv_modify);
            deleteButton = itemView.findViewById(R.id.iv_delete);
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
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AdminClassModifyFragment(currentItem));
                fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
                fragmentTransaction.commit();
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click
            }
        });
    }

    @Override
    public int getItemCount() {
        if (filteredList != null) {
            return filteredList.size();
        } else return adminClassList.size();

    }

    public void filter(String query, String filterType) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(adminClassList);
        } else {
            for (AdminClass item : adminClassList) {
                if ((filterType.equals("Tìm theo mã số") && item.getClassId().toLowerCase().contains(query.toLowerCase())) ||
                        (filterType.equals("Tìm theo tên") && item.getClassSubject().toLowerCase().contains(query.toLowerCase()))) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
