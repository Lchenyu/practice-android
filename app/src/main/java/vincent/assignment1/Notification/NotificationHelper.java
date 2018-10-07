package vincent.assignment1.Notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import vincent.assignment1.R;

import static vincent.assignment1.Notification.AppNotification.CHANNEL_1_ID;


/**
 * @author Yu Liu
 *
 * To create a notification and put it into a channel
 */

public class NotificationHelper {

    public void sendOnChannel1(String title, String message, Context context) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Intent intent = new Intent(context, ResecheduleReceiver.class);
        intent.putExtra("toastMessage", "Reminder Rescheduled");

        PendingIntent actionIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notify_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher,"Remind me later", actionIntent)
                .build();

        notificationManager.notify(1, notification);
    }
}
