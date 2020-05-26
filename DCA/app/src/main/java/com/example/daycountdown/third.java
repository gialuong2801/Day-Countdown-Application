package com.example.daycountdown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class third extends AppCompatActivity {
    private final static String TAG = "third";

    private final String CHANNEL_ID = "NewNoti";
    private final int NOTIFICATION_ID = 001;

    private Button buttonstart;
    private Button buttonstop;
    private Toolbar toolbar;
    private TextView DisplayDate;
    private DatePickerDialog.OnDateSetListener DateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        DisplayDate = findViewById(R.id.choosedate);

        toolbar = findViewById(R.id.toolbar1);
        TextView toolbarTitle = findViewById(R.id.tab1);

        toolbar.setTitle("");
        toolbarTitle.setText("Optional Countdown");
        setSupportActionBar(toolbar);

        DisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(third.this, android.R.style.Theme_Holo_Dialog_MinWidth, (DatePickerDialog.OnDateSetListener) DateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, final int year, int month, final int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy " + year + "/" + month + "/" + day);

                String date = month + "/" + day + "/" + year;
                DisplayDate.setText(date);

                buttonstart = (Button) findViewById(R.id.startbutton);
                final int finalMonth = month;
                buttonstart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        int pYear = c.get(Calendar.YEAR);
                        int pMonth = c.get(Calendar.MONTH);
                        int pDay = c.get(Calendar.DAY_OF_MONTH);

                        if ( year < pYear )
                            DisplayDate.setText("You must input one day in the future!");
                        if ( year > pYear )
                            startcd();
                        if (year == pYear){
                            if (finalMonth-1 < pMonth)
                                DisplayDate.setText("You must input one day in the future!");
                            if (finalMonth-1 > pMonth)
                                startcd();
                            if (finalMonth-1 == pMonth){
                                if (day <= pDay)
                                    DisplayDate.setText("You must input one day in the future!");
                                else startcd();
                            }
                         }
                    }
                    private void startcd() {
                        Calendar start_calendar = Calendar.getInstance();
                        Calendar end_calendar = Calendar.getInstance();

                        end_calendar.set(year, finalMonth -1, day, 0, 0, 0);
                        long start_millis = start_calendar.getTimeInMillis();
                        long end_millis = end_calendar.getTimeInMillis();
                        long total_millis = end_millis - start_millis;

                        final CountDownTimer cdm = new CountDownTimer(total_millis, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                                DisplayDate.setText(days + ":" + hours + ":" + minutes + ":" + seconds);

                            }

                            @Override
                            public void onFinish() {
                                createNotificationChannel();
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(third.this,CHANNEL_ID);
                                builder.setSmallIcon(R.drawable.ic_message);
                                builder.setContentTitle("New Notification");
                                builder.setContentText("It's time for your event!");
                                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(third.this);
                                notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

                                DisplayDate.setText("NOW! IT'S YOUR TIME!!!");
                            }
                        };
                        cdm.start();

                        Button buttonstop;
                        buttonstop = (Button) findViewById(R.id.stopbutton);
                        buttonstop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cdm.cancel();
                                DisplayDate.setText("Select Date");
                            }
                        });
                    }
                });
            }
        };
    }


    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "My New Notification";
            String description = "Notification for events";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
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

            Intent intent = new Intent(third.this, second.class);
            startActivity(intent);

            return true;
        }
        else
        if (id == R.id.item1){

            Intent intent = new Intent(third.this, first.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
