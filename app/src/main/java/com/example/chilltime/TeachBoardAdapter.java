package com.example.chilltime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeachBoardAdapter extends RecyclerView.Adapter<TeachBoardAdapter.TeachBoardViewHolder> {
    private final Context context;
    private List<List<String>> data;
    private String classId;

    public TeachBoardAdapter(Context context, List<List<String>> data, String classId) {
        this.context = context;
        this.data = data;
        this.classId = classId;
    }

    public static class TeachBoardViewHolder extends RecyclerView.ViewHolder {
        TextView column1, column2, column3, column4, column5, column6, column7;
        HorizontalScrollView scrollView;

        public TeachBoardViewHolder(View itemView) {
            super(itemView);
            column1 = itemView.findViewById(R.id.tvColumn1);
            column2 = itemView.findViewById(R.id.tvColumn2);
            column3 = itemView.findViewById(R.id.tvColumn3);
            column4 = itemView.findViewById(R.id.tvColumn4);
            column5 = itemView.findViewById(R.id.tvColumn5);
            column6 = itemView.findViewById(R.id.tvColumn6);
            column7 = itemView.findViewById(R.id.tvColumn7);
            scrollView = itemView.findViewById(R.id.scrollView);
        }
    }

    @Override
    public TeachBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_tboard, parent, false);
        return new TeachBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeachBoardViewHolder holder, int position) {
        List<String> row = data.get(position);
        holder.column1.setText(row.get(0)); // STT
        holder.column2.setText(row.get(1)); // Họ và tên
        holder.column3.setText(row.get(2)); // QT
        holder.column4.setText(row.get(3)); // TH
        holder.column5.setText(row.get(4)); // GK
        holder.column6.setText(row.get(5)); // CK
        holder.column7.setText(row.get(6)); // TB

        if (position == 0) {
            // Thiết lập giao diện cho hàng đầu tiên (Header)
            holder.itemView.setBackgroundColor(Color.parseColor("#DCE1EF"));
            holder.column1.setTextColor(Color.BLACK);
            holder.column2.setTextColor(Color.BLACK);
            holder.column3.setTextColor(Color.BLACK);
            holder.column4.setTextColor(Color.BLACK);
            holder.column5.setTextColor(Color.BLACK);
            holder.column6.setTextColor(Color.BLACK);
            holder.column7.setTextColor(Color.BLACK);

            holder.column1.setTypeface(null, android.graphics.Typeface.BOLD);
            holder.column2.setTypeface(null, android.graphics.Typeface.BOLD);
            holder.column3.setTypeface(null, android.graphics.Typeface.BOLD);
            holder.column4.setTypeface(null, android.graphics.Typeface.BOLD);
            holder.column5.setTypeface(null, android.graphics.Typeface.BOLD);
            holder.column6.setTypeface(null, android.graphics.Typeface.BOLD);
            holder.column7.setTypeface(null, android.graphics.Typeface.BOLD);

            // Ngăn không cho hàng đầu tiên được nhấn
            holder.scrollView.setOnClickListener(null);
        } else {
            // Thiết lập giao diện cho các hàng khác
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.column1.setTextColor(Color.BLACK);
            holder.column2.setTextColor(Color.BLACK);
            holder.column3.setTextColor(Color.BLACK);
            holder.column4.setTextColor(Color.BLACK);
            holder.column5.setTextColor(Color.BLACK);
            holder.column6.setTextColor(Color.BLACK);
            holder.column7.setTextColor(Color.BLACK);

            // Xử lý sự kiện nhấn vào phần tử
            holder.scrollView.setOnClickListener(v -> {
                Intent intent = new Intent(context, TeacherBroadInfo.class);

                // Truyền tất cả các cột qua Intent
                intent.putExtra("teacherId", row.get(0)); // ID
                intent.putExtra("teacherName", row.get(1)); // Họ và tên
                intent.putExtra("classId",classId);
                intent.putExtra("qt", row.get(2)); // QT
                intent.putExtra("th", row.get(3)); // TH
                intent.putExtra("gk", row.get(4)); // GK
                intent.putExtra("ck", row.get(5)); // CK
                intent.putExtra("tb", row.get(6)); // TB

                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
