package vincent.assignment1.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import vincent.assignment1.Notification.AlertReceiver;
import vincent.assignment1.R;
import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.model.SimpleTracking;

import static vincent.assignment1.view.MainActivity.TAG_STAGE;

public class AddNotifyActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView meetTimeView;
    private TextView timeBeforeView;
    private Button notifyBtn;
    private Spinner timeSpinner;
    private int minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notify);

        titleView = (TextView) findViewById(R.id.notify_tracking_title);
        meetTimeView = (TextView) findViewById(R.id.notify_tracking_meet_time);
        timeBeforeView = (TextView) findViewById(R.id.notify_time_before);
        notifyBtn = (Button) findViewById(R.id.notify_set_notify_btn);
        timeBeforeView.setText("Notify before (minute)");

        String trackingid = getIntent().getStringExtra("tracking_id");



        timeSpinner = (Spinner) findViewById(R.id.notify_time_spinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"1", "2", "3", "4", "5"}
        );

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        timeSpinner.setAdapter(spinnerAdapter);


        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                minute = Integer.valueOf(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final SimpleTracking trackingObject = getTrackingObject(trackingid);

        if(trackingObject != null){
            titleView.setText(trackingObject.getTilte());
            meetTimeView.setText(trackingObject.getMeetTime().toString());

            notifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG_STAGE + getClass().getName(), "set notification button clicked");
                    startAlarm(trackingObject);
                    finish();
                }
            });
        }
    }

    public void startAlarm(SimpleTracking tracking) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);

        intent.putExtra("addedTracking", tracking.getTilte());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        //will be taken from user input
        long oneMinuteInMilli = 2 * 60000;


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getNotifyDate(tracking.getMeetTime()), pendingIntent);

    }

    private Long getNotifyDate(Date meetTime){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 07);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.HOUR_OF_DAY, meetTime.getHours());
        cal.set(Calendar.MINUTE, meetTime.getMinutes());

        cal.add(Calendar.MINUTE, -minute);

        Log.d(TAG_STAGE + getClass().getName(), "notify time: " + cal.getTime());

        return cal.getTimeInMillis();
    }

    private SimpleTracking getTrackingObject(String trackingid){
        for (SimpleTracking object : TrackingHolder.getINSTANCE().getTrackingList()){
            if(object.getTrackingID().equals(trackingid)){
                return object;
            }
        }
        return null;
    }

}
