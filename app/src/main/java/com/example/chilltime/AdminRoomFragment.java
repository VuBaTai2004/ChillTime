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

public class AdminRoomFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdminRoomAdapter adminRoomAdapter;
    private ArrayList<AdminRoom> rooms;
    private Button btn_add_room;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_room, container,false);
        recyclerView=rootView.findViewById(R.id.rv_admin_room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        rooms = new ArrayList<>();
        rooms.add(new AdminRoom("C309", "IT001.P01"));
        rooms.add(new AdminRoom("B1.20", "IT002.P02"));
        rooms.add(new AdminRoom("B4.18", "IT003.P03"));

        adminRoomAdapter =new AdminRoomAdapter(getContext(),rooms);
        recyclerView.setAdapter(adminRoomAdapter);

        String[] searchList = {"Tìm phòng","Tìm mã lớp"};
        Spinner spinner_admin_subject_search = rootView.findViewById(R.id.spinner_admin_room_search);;
        ArrayAdapter<String> adapterSearchList = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,searchList);
        adapterSearchList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_admin_subject_search.setAdapter(adapterSearchList);

        btn_add_room=rootView.findViewById(R.id.btn_admin_add_room);
        btn_add_room.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminRoomAddFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });

        return rootView;
    }
}