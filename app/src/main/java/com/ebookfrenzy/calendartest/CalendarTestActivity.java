package com.ebookfrenzy.calendartest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.DatePicker;
import java.util.Calendar;

public class CalendarTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_test);

        TestCalendarData t = new TestCalendarData();
        t.test();

        //CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        //calendarView.setDate(System.currentTimeMillis());

        //DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        //datePicker.init(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, null);
        //datePicker.updateDate(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
    }
}
