package com.example.chilltime;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
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
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        // Lấy dữ liệu username từ Bundle
        String username = getArguments() != null ? getArguments().getString("username") : null;

        scheduleMap = new HashMap<>();

        db.collection("teachers").whereEqualTo("username", username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot teacherDoc : task.getResult()) {
                            String teacherId = teacherDoc.getId();

                            db.collection("teachers").document(teacherId).collection("class_list").get()
                                    .addOnCompleteListener(classTask -> {
                                        if (classTask.isSuccessful()) {
                                            for (QueryDocumentSnapshot classDoc : classTask.getResult()) {
                                                String classId = classDoc.getString("classId");

                                                // Truy xuất sang courses_detail để lấy thông tin
                                                db.collection("courses_detail").document(classId).get()
                                                        .addOnCompleteListener(courseTask -> {
                                                            if (courseTask.isSuccessful() && courseTask.getResult() != null) {
                                                                DocumentSnapshot courseDoc = courseTask.getResult();

                                                                // Lấy thông tin từ courses_detail
                                                                String startTime = courseDoc.getString("timeStart");
                                                                String endTime = courseDoc.getString("timeEnd");
                                                                String dayOfWeek = courseDoc.getString("dayofWeek");
                                                                String time = courseDoc.getString("time");
                                                                String room = courseDoc.getString("room");

                                                                // Parse ngày bắt đầu và kết thúc
                                                                Calendar startDate = Calendar.getInstance();
                                                                Calendar endDate = Calendar.getInstance();
                                                                try {
                                                                    startDate.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(startTime));
                                                                    endDate.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(endTime));
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }

                                                                // Thêm sự kiện vào lịch theo ngày trong tuần
                                                                while (!startDate.after(endDate)) {
                                                                    if (startDate.get(Calendar.DAY_OF_WEEK) == getDayOfWeek(dayOfWeek)) {
                                                                        CalendarDay calendarDay = CalendarDay.from(
                                                                                startDate.get(Calendar.YEAR),
                                                                                startDate.get(Calendar.MONTH),
                                                                                startDate.get(Calendar.DAY_OF_MONTH)
                                                                        );

                                                                        // Gán thông tin vào lịch
                                                                        addActivityToDate(calendarDay, new Activity(
                                                                                time,
                                                                                classId,
                                                                                room
                                                                        ));
                                                                    }
                                                                    startDate.add(Calendar.DATE, 1); // Chuyển sang ngày tiếp theo
                                                                }
                                                            } else {
                                                                Log.w("Firestore", "Error getting course details: ", courseTask.getException());
                                                            }
                                                        });
                                            }

                                            // Cập nhật RecyclerView với ngày hiện tại
                                            loadActivitiesForDate(CalendarDay.today());
                                        } else {
                                            Log.w("Firestore", "Error getting class list: ", classTask.getException());
                                        }
                                    });
                        }
                    } else {
                        Log.w("Firestore", "No teacher found or error: ", task.getException());
                    }
                });



        loadActivitiesForDate(CalendarDay.today());
        calendarView.setSelectedDate(CalendarDay.today());
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
                calendarView.setCurrentDate(date, true);
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

    private int getDayOfWeek(String day) {
        switch (day.toLowerCase()) {
            case "mon": return Calendar.MONDAY;
            case "tue": return Calendar.TUESDAY;
            case "wed": return Calendar.WEDNESDAY;
            case "thu": return Calendar.THURSDAY;
            case "fri": return Calendar.FRIDAY;
            case "sat": return Calendar.SATURDAY;
            case "sun": return Calendar.SUNDAY;
            default: return -1; // Giá trị không hợp lệ
        }
    }


}