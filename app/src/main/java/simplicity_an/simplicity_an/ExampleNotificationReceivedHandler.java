package simplicity_an.simplicity_an;

import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by kuppusamy on 10/22/2016.
 */
public class ExampleNotificationReceivedHandler  implements OneSignal.NotificationReceivedHandler {
String type;

    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
        String customKey;

        if (data != null) {
            customKey = data.optString("id", null);
            type=data.optString("type",null);
            if (customKey != null)
                Log.i("OneSignalExample", "customkey set with value: " + customKey);
        }
    }
}
