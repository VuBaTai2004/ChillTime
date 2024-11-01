package com.example.chilltime;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TeacherScheduleFragment extends Fragment {
    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;
    private List<Activity> activityList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_schedule, container, false);
        MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activityList = new ArrayList<>();
        activityAdapter = new ActivityAdapter(activityList);
        recyclerView.setAdapter(activityAdapter);
        loadActivitiesForDate(CalendarDay.today());

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)   // Set Monday as the first day of the week
                .setCalendarDisplayMode(CalendarMode.WEEKS) // Display mode to week
                .commit();

        calendarView.setTopbarVisible(false); // Hide the top bar with month and year
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                // Load activities for the selected date
                loadActivitiesForDate(date);
            }
        });

        return view;
    }

    private void loadActivitiesForDate(CalendarDay date) {
        // Clear the existing activities
        activityList.clear();

        // Sample data for demonstration; replace with your data source
        activityList.add(new Activity("08:00 - 09:30", "NT532.P11"));
        activityList.add(new Activity("10:00 - 11:30", "NT118.P13"));
        activityList.add(new Activity("13:00 - 14:30", "NT113.P11"));

        // Notify adapter of data changes
        activityAdapter.notifyDataSetChanged();
    }

}