package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class TeacherExerciseWatchAdapter extends RecyclerView.Adapter<TeacherExerciseWatchAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<String> studentDataList; // Lưu danh sách chuỗi dữ liệu

    public TeacherExerciseWatchAdapter(Context context, ArrayList<String> studentDataList) {
        this.context = context;
        this.studentDataList = studentDataList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentInfoTextView;
        public ImageView arrowIcon;
        public ConstraintLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            studentInfoTextView = itemView.findViewById(R.id.people_name);
            arrowIcon = itemView.findViewById(R.id.arrow_icon1);
            itemLayout = itemView.findViewById(R.id.item_people_child);
        }
    }

    @Override
    public TeacherExerciseWatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_people, parent, false);
        return new TeacherExerciseWatchAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeacherExerciseWatchAdapter.ViewHolder holder, int position) {
        String studentData = studentDataList.get(position);

        // Tách tên từ chuỗi dữ liệu
        String name = extractNameFromData(studentData);
        holder.studentInfoTextView.setText(name); // Hiển thị chỉ tên sinh viên

        holder.itemLayout.setOnClickListener(v -> {
            String[] splitData = studentData.split(", Link: ");
            if (splitData.length == 2) {
                String link = splitData[1].trim();
                Log.d("DriveLink", "Opening link: " + link);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                context.startActivity(intent);
            } else {
                Log.e("DriveLinkError", "Invalid data format: " + studentData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentDataList.size();
    }

    /**
     * Hàm trích xuất tên từ chuỗi dữ liệu
     */
    private String extractNameFromData(String data) {
        String[] splitData = data.split(", ID: "); // Phân tách trước phần ID
        if (splitData.length > 0) {
            return splitData[0].replace("Name: ", "").trim(); // Loại bỏ tiền tố "Name: " và khoảng trắng
        }
        return "Unknown Name"; // Trả về tên mặc định nếu không có thông tin
    }
}