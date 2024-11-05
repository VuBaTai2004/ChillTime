package com.example.chilltime;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class CustomSelectorDecorator implements DayViewDecorator {
    private CalendarDay selectedDate;

    public CustomSelectorDecorator(CalendarDay selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void setSelectedDate(CalendarDay date) {
        this.selectedDate = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(selectedDate);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.WHITE));
    }
}
