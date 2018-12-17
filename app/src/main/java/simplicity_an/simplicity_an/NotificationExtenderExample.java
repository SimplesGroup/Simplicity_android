package simplicity_an.simplicity_an;

import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

/**
 * Created by kuppusamy on 10/22/2016.
 */
public class NotificationExtenderExample extends NotificationExtenderService {
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                int color = ContextCompat.getColor(getApplicationContext(), R.color.notificationcolor);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setSmallIcon(R.mipmap.citynotify)
                            .setColor(color);



                    // builder.setColor(color);
                } else {
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            . setColor(color);

                }
             return builder;
            }
        };

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);
        Log.e("OneSignalExample","Notification displayed with id: " + displayedResult.androidNotificationId);
        return true;
    }
}
