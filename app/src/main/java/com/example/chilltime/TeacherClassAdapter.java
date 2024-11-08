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

public class TeacherClassAdapter extends RecyclerView.Adapter<TeacherClassAdapter.TeacherClassViewHolder> {
    private final Context context;
    private final ArrayList<AdminClass> adminClassList;
    private ArrayList<AdminClass> filteredList;

    public TeacherClassAdapter(Context context, ArrayList<AdminClass> adminClassList) {
        this.adminClassList = adminClassList;
        this.context = context;
        this.filteredList = new ArrayList<>(adminClassList);
    }

    public static class TeacherClassViewHolder extends RecyclerView.ViewHolder {
        public TextView classIdTextView;
        public TextView classSubjectTextView;
        // ... other views

        public TeacherClassViewHolder(View itemView) {
            super(itemView);
            classIdTextView = itemView.findViewById(R.id.tv_class_id);
            classSubjectTextView = itemView.findViewById(R.id.tv_class_subject);
            // ... initialize other views
        }
    }

    @NonNull
    @Override
    public TeacherClassAdapter.TeacherClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.teacher_class_adapter, parent, false);
        return new TeacherClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherClassViewHolder holder, int position) {
        AdminClass currentItem = adminClassList.get(position);
        holder.classIdTextView.setText(currentItem.getClassId());
        holder.classSubjectTextView.setText(currentItem.getClassSubject());
//        holder.editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle edit button click
//                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, new AdminClassModifyFragment(currentItem));
//                fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
//                fragmentTransaction.commit();
//            }
//        });
//        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle delete button click
//            }
//        });
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
