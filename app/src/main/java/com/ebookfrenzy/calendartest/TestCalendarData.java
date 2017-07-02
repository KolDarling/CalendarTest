package com.ebookfrenzy.calendartest;

/**
 * Created by chris on 2017-06-25.
 */

import java.text.SimpleDateFormat;
import java.util.*;
//import java.time.*;

public class TestCalendarData {

    private static final int DAYS_PER_WEEK = 7;

    public TestCalendarData() {
    }

    public void test() {
        Calendar calendar = Calendar.getInstance();
        GregorianCalendar currentDate = new GregorianCalendar(
                TimeZone.getTimeZone("America/Los_Angeles"),
                Locale.US
        );

        GregorianCalendar firstOfMonth = (GregorianCalendar) currentDate.clone();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeekOfMonth = firstOfMonth.get(Calendar.DAY_OF_WEEK);
        int weeksInMonth = currentDate.getActualMaximum(Calendar.WEEK_OF_MONTH);

        int dayView[][] = new int[weeksInMonth] [DAYS_PER_WEEK];

        // Prefill before the first of the month
        int daysInPrevMonth = getDaysInPreviousMonth(currentDate);
        int row = 0;
        int column = firstDayOfWeekOfMonth - 2;
        while (column >= 0) {
            dayView[row] [column] = daysInPrevMonth--;
            column--;
        }

        // Initialize the days of the current month
        int daysInMonth = getDaysInMonth(currentDate);
        column = firstDayOfWeekOfMonth - 1;
        for (int i = 0; i < daysInMonth; i++) {
            dayView[row] [column] = i + 1;
            column = (column + 1) % DAYS_PER_WEEK;
            if (column == 0) {
                row++;
            }
        }

        // Postfill to the end of the array
        int dayOfMonth = 1;
        while (column < DAYS_PER_WEEK) {
            dayView[weeksInMonth - 1] [column] = dayOfMonth++;
            column++;
        }

        String s1 = String.format("{0} {1} {2} {3} {4} {5} {6}",
                dayView[row][0],
                dayView[row][1],
                dayView[row][2],
                dayView[row][3],
                dayView[row][4],
                dayView[row][5],
                dayView[row][6]
        );

    }

    private static int getDaysInMonth(Calendar date) {
        return date.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private static int getDaysInPreviousMonth(Calendar date) {
        int currentMonthNumber = date.get(Calendar.MONTH);
        int prevMonthNumber =  ((currentMonthNumber + 11) % 12);
        Calendar prevMonth = (Calendar) date.clone();
        prevMonth.set(Calendar.MONTH, prevMonthNumber);
        return prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
