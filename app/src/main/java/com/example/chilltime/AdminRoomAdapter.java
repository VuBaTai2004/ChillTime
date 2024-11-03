package com.example.chilltime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminRoomAdapter extends RecyclerView.Adapter<AdminRoomAdapter.RoomViewHolder> {
    private Context context;
    private ArrayList<AdminRoom> rooms;

    public AdminRoomAdapter(Context context, ArrayList<AdminRoom> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public AdminRoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_room_adapter, parent, false);
        return new AdminRoomAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRoomAdapter.RoomViewHolder holder, int position) {
        AdminRoom room = rooms.get(position);

        // Hiển thị phòng và lớp học phòng đó
        holder.roomID.setText(room.getRoomId());
        holder.classId.setText(room.getClassId());

        // Xử lý sự kiện khi nhấn nút chỉnh sửa và xóa (nếu cần)
        holder.editButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminRoomModifyFragment(room));
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Xử lý xóa môn học
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView roomID, classId;
        ImageButton editButton, deleteButton;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomID = itemView.findViewById(R.id.admin_room_adapter_name);
            classId = itemView.findViewById(R.id.admin_room_adapter_class_id);
            editButton = itemView.findViewById(R.id.admin_room_adapter_edit);
            deleteButton = itemView.findViewById(R.id.admin_room_adapter_delete);
        }
    }

}
