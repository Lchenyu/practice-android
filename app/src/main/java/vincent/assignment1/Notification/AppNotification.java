package vincent.assignment1.Notification;

import android.app.Application;
import android.os.Build;
import android.app.NotificationChannel;
import android.app.NotificationManager;

/**
 * @author Yu Liu
 *
 * application should be registered on "AndroidManifest.xml" before use the channel.
 * if not register this App, the notification won't work.
 *
 * NOTICE: "NotificationCompat.Builder" constructor requires that you provide a channel ID which is created by this class.
 * This is required for compatibility with Android 8.0(API level 26) and higher, but is ignored by older versions
 */

public class AppNotification extends Application {

    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {

        //This is required for compatibility with Android 8.0(API level 26) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }
}
