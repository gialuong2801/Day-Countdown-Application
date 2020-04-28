package com.example.daycountdown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
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

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class third extends AppCompatActivity {
    private final static String TAG = "third";

    private Button button;
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

                button = (Button) findViewById(R.id.startbutton);
                final int finalMonth = month;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        int pYear = c.get(Calendar.YEAR);
                        int pMonth = c.get(Calendar.MONTH);
                        int pDay = c.get(Calendar.DAY_OF_MONTH);
                        if ( year < pYear )
                            DisplayDate.setText("You must input one day in the future!");
                        else if ( year > pYear )
                            startcd();
                            else if (finalMonth < pMonth)
                                DisplayDate.setText("You must input one day in the future!");
                                else if (finalMonth > pMonth)
                                    startcd();
                                    else if (day < pDay)
                                        DisplayDate.setText("You must input one day in the future!");
                                        else startcd();
                    }

                    private void startcd() {
                        Calendar start_calendar = Calendar.getInstance();
                        Calendar end_calendar = Calendar.getInstance();

                        end_calendar.set(year, finalMonth -1, day, 0, 0, 0);
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

                                DisplayDate.setText(days + ":" + hours + ":" + minutes + ":" + seconds);
                            }

                            @Override
                            public void onFinish() {
                                DisplayDate.setText("NOW! IT'S YOUR TIME!!!");
                            }
                        };
                        cdm.start();
                    }
                });
            }
        };
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
