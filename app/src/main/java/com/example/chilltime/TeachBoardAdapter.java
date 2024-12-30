package com.example.chilltime;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeachBoardAdapter extends RecyclerView.Adapter<TeachBoardAdapter.TeachBoardViewHolder> {
    private final Context context;
    private List<List<String>> data;

    public TeachBoardAdapter(Context context, List<List<String>> data) {
        this.context = context;
        this.data = data;
    }

    public static class TeachBoardViewHolder extends RecyclerView.ViewHolder {
        TextView column1, column2, column3, column4, column5, column6, column7;

        public TeachBoardViewHolder(View itemView) {
            super(itemView);
            // Khởi tạo các thành phần giao diện của item
            column1 = itemView.findViewById(R.id.tvColumn1);
            column2 = itemView.findViewById(R.id.tvColumn2);
            column3 = itemView.findViewById(R.id.tvColumn3);
            column4 = itemView.findViewById(R.id.tvColumn4);
            column5 = itemView.findViewById(R.id.tvColumn5);
            column6 = itemView.findViewById(R.id.tvColumn6);
            column7 = itemView.findViewById(R.id.tvColumn7);
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
            holder.itemView.setBackgroundColor(Color.parseColor("#DCE1EF")); // Màu nền khác biệt
            holder.column1.setTextColor(Color.BLACK); // Màu chữ nổi bật
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
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE); // Màu nền mặc định cho các hàng khác
            holder.column1.setTextColor(Color.BLACK); // Màu chữ của các dòng khác
            holder.column2.setTextColor(Color.BLACK);
            holder.column3.setTextColor(Color.BLACK);
            holder.column4.setTextColor(Color.BLACK);
            holder.column5.setTextColor(Color.BLACK);
            holder.column6.setTextColor(Color.BLACK);
            holder.column7.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
