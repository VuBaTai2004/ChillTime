package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.TeacherViewHolder> {
    private final Context context;
    private final ArrayList<TeacherProfile> teacherList;

    public TeacherListAdapter(Context context, ArrayList<TeacherProfile> teacherList) {
        this.teacherList = teacherList;
        this.context = context;
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        public TextView teacherNameTextView;
        public TextView teacherIdTextView;
        public TextView teacherPhoneTextView;
        public TextView teacherEmailTextView;
        public ImageView arrowIcon;
        public TeacherViewHolder(View itemView) {
            super(itemView);
            teacherNameTextView = itemView.findViewById(R.id.people_name);

            arrowIcon = itemView.findViewById(R.id.arrow_icon1);
        }

    }
    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_people, parent, false);
        return new TeacherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {
        TeacherProfile currentItem = teacherList.get(position);
        holder.teacherNameTextView.setText(currentItem.getName());
        holder.arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeacherListInfo.class);
                intent.putExtra("teacherName", currentItem.getName());
                intent.putExtra("teacherId", currentItem.getId());
                intent.putExtra("teacherPhone", currentItem.getPhone());
                intent.putExtra("teacherEmail", currentItem.getEmail());
                context.startActivity(intent);

            }
        });

    }
    @Override
    public int getItemCount() {
        return teacherList.size();
    }
}
