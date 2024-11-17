package com.example.chilltime;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherScheduleFragment extends Fragment {
    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;
    private List<Activity> activityList;
    private Map<CalendarDay, List<Activity>> scheduleMap;
    private CustomSelectorDecorator customSelectorDecorator;
    private TextView txtDay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_schedule, container, false);
        MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);
        txtDay = view.findViewById(R.id.txtDay);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activityList = new ArrayList<>();
        activityAdapter = new ActivityAdapter(activityList);
        recyclerView.setAdapter(activityAdapter);

        scheduleMap = new HashMap<>();

        addActivityToDate(CalendarDay.from(2024, 9, 28), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 9, 28), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 9, 29), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 9, 30), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 9, 30), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 9, 31), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 10, 1), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 10, 1), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 10, 2), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 10, 3), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 10, 3), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 10, 4), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 10, 5), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 10, 5), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 10, 6), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 10, 7), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 10, 7), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 10, 8), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 10, 9), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 10, 9), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 10, 10), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 10, 11), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 10, 11), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 10, 12), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 10, 13), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 10, 13), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 10, 14), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        addActivityToDate(CalendarDay.from(2024, 10, 15), new Activity("08:00 - 09:30", "NT532.P11", "B5.06"));
        addActivityToDate(CalendarDay.from(2024, 10, 15), new Activity("10:00 - 11:30", "NT118.P13", "C3.14"));
        addActivityToDate(CalendarDay.from(2024, 10, 16), new Activity("13:00 - 14:30", "NT113.P11", "B1.20"));

        loadActivitiesForDate(CalendarDay.today());
        calendarView.setCurrentDate(CalendarDay.today());
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        calendarView.setTopbarVisible(false);

        customSelectorDecorator = new CustomSelectorDecorator(CalendarDay.today());
        calendarView.addDecorator(customSelectorDecorator);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                loadActivitiesForDate(date);

                customSelectorDecorator.setSelectedDate(date);
                calendarView.invalidateDecorators();
                updateDayText(date);
            }
        });
        calendarView.setWeekDayFormatter(dayOfWeek -> {
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    return "CN";
                case Calendar.MONDAY:
                    return "T2";
                case Calendar.TUESDAY:
                    return "T3";
                case Calendar.WEDNESDAY:
                    return "T4";
                case Calendar.THURSDAY:
                    return "T5";
                case Calendar.FRIDAY:
                    return "T6";
                case Calendar.SATURDAY:
                    return "T7";
                default:
                    return ""; 
            }
        });

        calendarView.setSelectedDate(CalendarDay.today());
        return view;
    }

    private void addActivityToDate(CalendarDay date, Activity activity) {
        if (!scheduleMap.containsKey(date)) {
            scheduleMap.put(date, new ArrayList<>());
        }
        scheduleMap.get(date).add(activity);
    }

    private void loadActivitiesForDate(CalendarDay date) {
        activityList.clear();

        List<Activity> activitiesForDate = scheduleMap.get(date);

        if (activitiesForDate != null) {
            activityList.addAll(activitiesForDate);
        } else {
            Toast.makeText(requireContext(), "No activities for this date", Toast.LENGTH_SHORT).show();
        }

        updateDayText(date);
        activityAdapter.notifyDataSetChanged();
    }

    private void updateDayText(CalendarDay date) {
        String[] daysOfWeek = {"Chủ Nhật", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"};
        Calendar calendar = Calendar.getInstance();

        calendar.set(date.getYear(), date.getMonth() , date.getDay());

        String dayOfWeek = daysOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1];

        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();

        String formattedDate = String.format("%s ngày %02d tháng %02d năm %d", dayOfWeek, day, month + 1, year);
        txtDay.setText(formattedDate);
    }

}