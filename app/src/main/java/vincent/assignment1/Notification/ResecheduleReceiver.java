package vincent.assignment1.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

public class ResecheduleReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, AlertReceiver.class);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);


        //will be taken from user input
        long oneMinuteInMilli = 60000;

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + oneMinuteInMilli), pendingIntent);

        NotificationManagerCompat.from(context).cancel(2);
    }
}
