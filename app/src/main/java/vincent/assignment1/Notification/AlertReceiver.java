package vincent.assignment1.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationHelper notificationHelper = new NotificationHelper();
        String title = intent.getExtras().getString("addedTracking");
        notificationHelper.sendOnChannel1(title + " Tracking Reminder!",  title+ " is reaching meet location soon.", context);
    }
}
