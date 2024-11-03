package com.example.chilltime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class StudentClassFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_class, container, false);
        // Inflate the layout for this fragment
        Spinner spinner = view.findViewById(R.id.id_year);
        ArrayList<String> arrrayYear = new ArrayList<String>();
        arrrayYear.add("2021");
        return view;

    }
}