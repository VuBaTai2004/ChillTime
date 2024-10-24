package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AdminRoomFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdminRoomAdapter adapter;
    private ArrayList<AdminRoom> rooms;

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

        adapter=new AdminRoomAdapter(getContext(),rooms);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}