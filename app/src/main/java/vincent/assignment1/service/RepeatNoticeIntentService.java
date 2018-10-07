package vincent.assignment1.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;

public class RepeatNoticeIntentService extends IntentService {


    public RepeatNoticeIntentService() {
        super("RepeatNoticeIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        AlarmManager alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent mintent = new Intent(this, NotificationIntentService.class);

        mintent.putExtra("title", intent.getStringExtra("title"));

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);


        long oneMinuteInMilli = 60000;

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + oneMinuteInMilli), pendingIntent);

    }

}
