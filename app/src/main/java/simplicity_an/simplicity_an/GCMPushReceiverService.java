package simplicity_an.simplicity_an;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Random;


/**
 * Created by Belal on 4/15/2016.
 */

//Class is extending GcmListenerService
public class GCMPushReceiverService extends GcmListenerService {
int Unique_Integer_Number;
    String type,ids;
    Intent intent;
    //This method will be called on every new message received
    @Override
    public void onMessageReceived(String from, Bundle data) {
        //Getting the message from the bundle
        String message = data.getString("title");
        type = data.getString("type");
       ids = data.getString("id");

        //Displaying a notiffication with the message
        sendNotification(message);
    }

    //This method is generating a notification and displaying the notification
    private void sendNotification(String message) {


        int requestCode = 0;
        Unique_Integer_Number=new Random().nextInt();


       /* PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)

                . setContentTitle("Simplicity")

                .setContentText(Html.fromHtml(type + "|&nbsp;" + message))
                .setSmallIcon(R.drawable.ic_launcher)

                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Unique_Integer_Number, noBuilder.build()); //0 = ID of notification

*/

        // Instantiate a Builder object.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
// Creates an Intent for the Activity
      /*  Intent notifyIntent =
                new Intent(this, ResultActivity.class);*/
// Sets the Activity to start in a new, empty task
       /* notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
    /* if(type.equals("news")) {
            intent = new Intent(getApplicationContext(), NewsDescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }else if(type.equals("article")){
            intent = new Intent(getApplicationContext(), Articledescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }else if (type.equals("doit")){
            intent = new Intent(getApplicationContext(), DoitDescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }else if(type.equals("farming")){
            intent = new Intent(getApplicationContext(), Farmingdescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }else if(type.equals("foodtip")){
            intent = new Intent(getApplicationContext(), TipsDescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }else if(type.equals("govt")){
            intent = new Intent(getApplicationContext(), GovernmentnotificationsDescriptions.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }else if(type.equals("health")){
            intent = new Intent(getApplicationContext(), Healthylivingdescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }else if(type.equals("science")){
            intent = new Intent(getApplicationContext(), ScienceandTechnologyDescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }else if(type.equals("sports")){
            intent = new Intent(getApplicationContext(), SportsnewsDescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }else if(type.equals("travels")){
            intent = new Intent(getApplicationContext(), TravelsDescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        }else if(type.equals("events")){
            intent = new Intent(getApplicationContext(), EventsDescription.class);
            intent.putExtra("ID", ids);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }else {
         intent = new Intent(getApplicationContext(), MainActivityVersiontwo.class);
         intent.putExtra("ID", ids);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                 | Intent.FLAG_ACTIVITY_CLEAR_TASK);
     }
*/
        intent = new Intent(getApplicationContext(), NewsDescription.class);
        intent.putExtra("ID", ids);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        Unique_Integer_Number,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                );
        builder   . setContentTitle("Simplicity")

               // .setContentText(Html.fromHtml(type + "|&nbsp;" + message))
                .setContentText(Html.fromHtml( type+"|&nbsp;" + message))
                .setSmallIcon(R.drawable.ic_launcher)

                .setAutoCancel(true);


// Puts the PendingIntent into the notification builder
        builder.setContentIntent(notifyPendingIntent);
// Notifications are issued by sending them to the
// NotificationManager system service.
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Builds an anonymous Notification object from the builder, and
// passes it to the NotificationManager
        mNotificationManager.notify(Unique_Integer_Number, builder.build());







    }

}
