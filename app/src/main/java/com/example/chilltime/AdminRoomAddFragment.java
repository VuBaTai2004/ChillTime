package com.example.chilltime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AdminRoomAddFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_room_add, container, false);
        Button btn = view.findViewById(R.id.admin_btn_add_room);
        ImageView iv = view.findViewById(R.id.admin_iv_back_add);
        iv.setOnClickListener(view1 -> {
            FragmentManager fragmentManager = ((AppCompatActivity) view1.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminRoomFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });
        btn.setOnClickListener(view2 -> {
            addClassToDB();
            FragmentManager fragmentManager = ((AppCompatActivity) view2.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AdminRoomFragment());
            fragmentTransaction.addToBackStack(null); // Optional: adds the transaction to the back stack
            fragmentTransaction.commit();
        });

        return view;
    }
    private void addClassToDB() {

    }
}
