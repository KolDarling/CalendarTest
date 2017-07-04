package com.ebookfrenzy.calendartest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class CalendarTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_test);

        // Get the current time
        GregorianCalendar currentDate = new GregorianCalendar(
                TimeZone.getTimeZone("America/Los_Angeles"),
                Locale.US
        );

        CalendarData t = new CalendarData(currentDate, currentDate);

        // Get the dates to display in the calendar view
        GregorianCalendar[][] calendarDates = t.getCalendarDates();

        TableLayout calendar = (TableLayout) findViewById(R.id.calendar);
        int initialRowCount = calendar.getChildCount();
        TableRow weekLabelRow = (TableRow) findViewById(R.id.days);

        TableRow monthRow = (TableRow) findViewById(R.id.month);
        TextView monthView = (TextView) monthRow.findViewById(R.id.textMonth);
        monthView.setText(currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US));

        TableRow yearRow = (TableRow) findViewById(R.id.year);
        TextView yearView = (TextView) yearRow.findViewById(R.id.textYear);
        yearView.setText(Integer.toString(currentDate.get(Calendar.YEAR)));

        for (int i = 0; i < calendarDates.length; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(weekLabelRow.getLayoutParams());
            GregorianCalendar[] week = calendarDates[i];
            for (int j = 0; j < week.length; j++) {
                TextView textView = new TextView(this);
                textView.setText(Integer.toString(week[j].get(Calendar.DAY_OF_MONTH)));
                textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                textView.setBackgroundResource(R.drawable.borders);
                textView.setPadding(0, 0, 0, 80);
                if (i == t.getTodayRow() && j == t.getTodayColumn()) {
                    textView.setTextColor(Color.RED);
                }
                textView.setOnClickListener(
                        new TextView.OnClickListener() {
                            public void onClick(View v) {
                                ((TextView) v).setTextColor(Color.GREEN);
                            }
                        }
                );
                row.addView(textView);
            }
            calendar.addView(row, i + initialRowCount);
        }
    }
}
