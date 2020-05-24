package com.example.daycountdown;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class second extends AppCompatActivity {
    TextView textView;

    private Toolbar toolbar;

    private final String CHANNEL_ID = "NewNoti";
    private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbar = findViewById(R.id.toolbar1);
        TextView toolbarTitle = findViewById(R.id.tab1);

        toolbar.setTitle("");
        toolbarTitle.setText("Mid-Autumn Countdown");
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.MidAutumnCountdown);

        Calendar start_calendar = Calendar.getInstance();
        Calendar end_calendar = Calendar.getInstance();

        end_calendar.set(2020, 9, 1, 0,0,0);

        long start_millis = start_calendar.getTimeInMillis();
        long end_millis = end_calendar.getTimeInMillis();
        long total_millis = end_millis - start_millis;

        CountDownTimer cdm = new CountDownTimer(total_millis, 1000) {
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

                if (seconds == 86400){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(second.this,CHANNEL_ID);
                    builder.setSmallIcon(R.drawable.ic_message);
                    builder.setContentTitle("New Notification");
                    builder.setContentText("It's one day left to your event!");
                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(second.this);
                    notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
                }
            }

            @Override
            public void onFinish() {
                textView.setText("HELLO MID-AUTUMN!!!!!!");
            }
        };
        cdm.start();
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

        if (id == R.id.item1){

            Intent intent = new Intent(second.this, first.class);
            startActivity(intent);

            return true;
        }
        else
        if (id == R.id.item3){

            Intent intent = new Intent(second.this, third.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
