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

public class AdminSubjectAdapter extends RecyclerView.Adapter<AdminSubjectAdapter.SubjectViewHolder> {

    private Context context;
    private ArrayList<AdminSubject> subjectList;

    public AdminSubjectAdapter(Context context, ArrayList<AdminSubject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_subject_adapter, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        AdminSubject subject = subjectList.get(position);

        // Hiển thị tên môn học và mã lớp
        holder.subjectId.setText(subject.getSubjectId());
        holder.name.setText(subject.getName());

        // Xử lý sự kiện khi nhấn nút chỉnh sửa và xóa (nếu cần)
        holder.editButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminSubjectModifyFragment(subject));
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Xử lý xóa môn học
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView name, subjectId;
        ImageButton editButton, deleteButton;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.admin_subject_adapter_name);
            subjectId = itemView.findViewById(R.id.admin_subject_adapter_id);
            editButton = itemView.findViewById(R.id.admin_subject_adapter_edit);
            deleteButton = itemView.findViewById(R.id.admin_subject_adapter_delete);
        }
    }
}
