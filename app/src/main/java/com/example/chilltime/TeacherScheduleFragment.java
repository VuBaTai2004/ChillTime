package com.example.chilltime;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class TeacherScheduleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_schedule, container, false);

        MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)   // Set Monday as the first day of the week
                .setCalendarDisplayMode(CalendarMode.WEEKS) // Display mode to week
                .commit();

        calendarView.setTopbarVisible(false); // Hide the top bar with month and year

        return view;
    }

}