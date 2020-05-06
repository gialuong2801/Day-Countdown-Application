package com.example.daycountdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class first extends AppCompatActivity {
    TextView textView;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        toolbar = findViewById(R.id.toolbar1);
        TextView toolbarTitle = findViewById(R.id.tab1);

        toolbar.setTitle("");
        toolbarTitle.setText("Tet Holiday Countdown");
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.NewYearCountdown);

        Calendar start_calendar = Calendar.getInstance();
        Calendar end_calendar = Calendar.getInstance();

        end_calendar.set(2021, 1, 12, 0,0,0);

        long start_millis = start_calendar.getTimeInMillis();
        long end_millis = end_calendar.getTimeInMillis();
        long total_millis = end_millis - start_millis;

        CountDownTimer cdny = new CountDownTimer(total_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                textView.setText(days + ":" + hours + ":" + minutes + ":" + seconds);
            }

            @Override
            public void onFinish() {
                textView.setText("HAPPY NEW YEAR!!!!!!");
            }
        };
        cdny.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cdmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item2){

            Intent intent = new Intent(first.this, second.class);
            startActivity(intent);

            return true;
        }
        else
        if (id == R.id.item3){

            Intent intent = new Intent(first.this, third.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
