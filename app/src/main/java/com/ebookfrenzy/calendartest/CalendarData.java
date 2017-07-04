package com.ebookfrenzy.calendartest;

/**
 * Created by chris on 2017-06-25.
 */

import java.text.SimpleDateFormat;
import java.util.*;
//import java.time.*;

public class CalendarData {

    private static final int DAYS_PER_WEEK = 7;

    // This datetime represents "today"
    private GregorianCalendar currentDate;

    // The month and year whose days we want to view
    private GregorianCalendar viewMonth;

    private int[][] calendarDays;
    private int todayRow;
    private int todayColumn;

    private GregorianCalendar[][] calendarDates;

    public CalendarData(GregorianCalendar currentDate, GregorianCalendar viewMonth) {
        this.currentDate = currentDate;
        this.viewMonth = viewMonth;
        this.viewMonth.set(Calendar.DAY_OF_MONTH, 1);
        this.init();
    }

    public int[][] getCalendarDays() {
        return this.calendarDays;
    }

    public GregorianCalendar[][] getCalendarDates() {
        return this.calendarDates;
    }

    // OptionalInt is a nullable int type. But it requires API version 24 or higher
    public int getTodayRow() {
        return this.todayRow;
    }

    public int getTodayColumn() {
        return this.todayColumn;
    }

    private void init() {
        int firstDayOfWeekOfMonth = this.viewMonth.get(Calendar.DAY_OF_WEEK);
        int weeksInMonth = this.viewMonth.getActualMaximum(Calendar.WEEK_OF_MONTH);

        // Allocate the space needed to view a month of days that spans the view month
        this.calendarDays = new int[weeksInMonth] [DAYS_PER_WEEK];
        this.calendarDates = new GregorianCalendar[weeksInMonth] [DAYS_PER_WEEK];

        // Set today's row and column assuming the view doesn't include today
        this.todayRow = -1;
        this.todayColumn = -1;

        GregorianCalendar prevMonthToView = (GregorianCalendar) this.viewMonth.clone();
        prevMonthToView.add(Calendar.MONTH, -1);
        GregorianCalendar nextMonthToView = (GregorianCalendar) this.viewMonth.clone();
        nextMonthToView.add(Calendar.MONTH, 1);

        // "today" will be especially highlighted if it is in the view
        int todayDayOfMonth = this.currentDate.get(Calendar.DAY_OF_MONTH);

        // Prefill before the first of the month
        int daysInPrevMonth = prevMonthToView.getActualMaximum(Calendar.DAY_OF_MONTH);
        int row = 0;
        int column = firstDayOfWeekOfMonth - 2;
        while (column >= 0) {
            if (daysInPrevMonth == todayDayOfMonth &&
                prevMonthToView.get(Calendar.MONTH) == this.currentDate.get(Calendar.MONTH)) {
                this.todayRow = row;
                this.todayColumn = column;
            }

            this.calendarDates[row] [column] = (GregorianCalendar) prevMonthToView.clone();
            this.calendarDates[row] [column].set(Calendar.DAY_OF_MONTH, daysInPrevMonth);
            this.calendarDays[row] [column] = daysInPrevMonth--;
            column--;
        }

        // Initialize the days of the month to be viewed
        int daysInMonth = this.viewMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        column = firstDayOfWeekOfMonth - 1;
        for (int i = 0; i < daysInMonth; i++) {
            this.calendarDates[row] [column] = (GregorianCalendar) this.viewMonth.clone();
            this.calendarDates[row] [column].set(Calendar.DAY_OF_MONTH, i + 1);
            this.calendarDays[row][column] = i + 1;

            if (todayDayOfMonth == calendarDays[row][column] &&
                this.viewMonth.get(Calendar.MONTH) == this.currentDate.get(Calendar.MONTH)) {
                this.todayRow = row;
                this.todayColumn = column;
            }
            column = (column + 1) % DAYS_PER_WEEK;
            if (column == 0) {
                row++;
            }
        }

        // Post-fill to the end of the array
        int day = 1;
        while (column < DAYS_PER_WEEK) {
            if (day == todayDayOfMonth &&
                nextMonthToView.get(Calendar.MONTH) == this.currentDate.get(Calendar.MONTH)) {
                this.todayRow = row;
                this.todayColumn = column;
            }

            this.calendarDates[weeksInMonth - 1] [column] = (GregorianCalendar) nextMonthToView.clone();
            this.calendarDates[weeksInMonth - 1] [column].set(Calendar.DAY_OF_MONTH, day);
            this.calendarDays[weeksInMonth - 1] [column] = day++;
            column++;
        }
    }
}
