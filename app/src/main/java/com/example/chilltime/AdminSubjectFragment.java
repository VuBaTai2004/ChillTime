package com.example.chilltime;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class AdminSubjectFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdminSubjectAdapter adminSubjectAdapter;
    private ArrayList<AdminSubject> subjectList;
    private Button btn_add_subject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_subject, container,false);

        recyclerView=rootView.findViewById(R.id.rv_admin_subject_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        subjectList=new ArrayList<>();
        subjectList.add(new AdminSubject("IT001", "Nhập môn lập trình  "));
        subjectList.add(new AdminSubject( "NT118","Phát triển ứng dụng trên thiết bị di động"));
        subjectList.add(new AdminSubject("NT532", "Công nghệ Internet Of Things hiện đại"));
        subjectList.add(new AdminSubject("SS010", "Lịch sử Đảng Cộng sản Việt Nam"));
        subjectList.add(new AdminSubject("NT505", "Khóa luận tốt nghiệp"));

        adminSubjectAdapter =new AdminSubjectAdapter(getContext(),subjectList);
        recyclerView.setAdapter(adminSubjectAdapter);

        String[] searchList = {"Tìm tên môn","Tìm mã lớp"};
        Spinner spinner_admin_subject_search = rootView.findViewById(R.id.spinner_admin_subject_search);;
        ArrayAdapter<String>adapterSearchList = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,searchList);
        adapterSearchList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_admin_subject_search.setAdapter(adapterSearchList);

        btn_add_subject=rootView.findViewById(R.id.btn_admin_add_subject);
        btn_add_subject.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminSubjectAddFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });

        return rootView;
    }
}