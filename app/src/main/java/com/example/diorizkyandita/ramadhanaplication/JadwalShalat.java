package com.example.diorizkyandita.ramadhanaplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class JadwalShalat extends AppCompatActivity {
    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    TimePickerDialog timePickerDiaog;
    final static int RQS_1 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_shalat);

        textAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
        buttonstartSetDialog = (Button)findViewById(R.id.button1);

        buttonstartSetDialog.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
            }
            private void openTimePickerDialog(boolean b) {
                Calendar calendar = Calendar.getInstance();
                timePickerDiaog = new TimePickerDialog(JadwalShalat.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
                timePickerDiaog.setTitle("set Alarm Time");

                timePickerDiaog.show();
            }
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {


                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Calendar calNow = Calendar.getInstance();
                    Calendar calSet = (Calendar) calNow.clone();

                    calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calSet.set(Calendar.MINUTE, minute);
                    calSet.set(Calendar.SECOND, 0);
                    calSet.set(Calendar.MILLISECOND, 0);

                    if (calSet.compareTo(calNow)<=0){
                        calSet.add(Calendar.DATE, 1);
                        Log.i("Hasil","=<0");
                    }else if(calSet.compareTo(calNow)>0){
                        Log.i("Hasil",">0");
                    }else{
                        Log.i("Hasil","else");
                    }
                    setAlarm(calSet);
                }
            };
            private void setAlarm(Calendar targerCal){
                textAlarmPrompt.setText("***\n" + "Alarm set on" + targerCal.getTime() + "\n***");


                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, targerCal.getTimeInMillis(),pendingIntent);
            }

        });
    }
    }

