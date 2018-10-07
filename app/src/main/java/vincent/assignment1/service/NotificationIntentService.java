package vincent.assignment1.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import vincent.assignment1.R;

import static vincent.assignment1.view.MainActivity.TAG_STAGE;

public class NotificationIntentService extends IntentService {


    public NotificationIntentService() {

        super("NotificationIntentService");
        Log.d(TAG_STAGE + getClass().getName(), "on constructor");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG_STAGE + getClass().getName(), "on onHandleIntent");

        createNotificationChannel();
        String title = intent.getStringExtra("title");
        createNotification(title);

    }


    private void createNotification(String text){

        Log.d(TAG_STAGE + getClass().getName(), "create notification");

        Intent intent = new Intent(this, RepeatNoticeIntentService.class);
        intent.putExtra("title", text);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(this, "Channel1")
                .setContentTitle("Tracking Notification")
                .setContentText("It is time to go to "+text)
                .setSmallIcon(R.drawable.ic_notify_24dp)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_remider_later_24dp,"Remid me later", pendingIntent)
                .build();

        manager.notify(1, notification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Channel1", "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel for tracking assignment");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
