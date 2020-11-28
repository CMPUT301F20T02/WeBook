package com.example.webook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button setTime;
    private String format = "";
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_set_time);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        setTime = findViewById(R.id.request_setTime_button);
        calendar = Calendar.getInstance();

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getHour();
                int min = timePicker.getMinute();
                System.out.println(showTime(hour, min));
                Intent intent2 = new Intent();
                intent2.putExtra
                        ("time", showTime(hour, min));
                setResult(RESULT_OK, intent2);
                finish();
            }
        });
    }
    public String showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        String finalTime = new StringBuilder().append(hour).append(" : ").append(min).append(" ").append(format).toString();
        return  finalTime;
    }
}
